package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findUserByEmail(userName).isEmpty()) {
            log.info("Такого пользователя не найдено");
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }


    @Override
    public boolean register(ru.skypro.homework.model.User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            log.info("Такой пользователь существует");
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(user.getPassword())
                        .username(user.getEmail())
                        .roles(user.getRole().name())
                        .build());
        userRepository.save(user);
        return true;
    }
}