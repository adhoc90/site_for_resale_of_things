package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.SecurityDetailsService;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SecurityDetailsService securityDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        log.info("Зарегистрированный пользователь пытается войти");
        if (userRepository.findUserByEmail(username).isEmpty()) {
            log.info("Такой пользователь отсутствует");
            return false;
        }
        log.info("username исправен {} " + username);
        UserDetails userDetails = securityDetailsService.loadUserByUsername(username);
        return encoder.matches(password, userDetails.getPassword());
    }


    @Override
    public boolean register(ru.skypro.homework.model.User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            log.info("Такой пользователь существует");
            return false;
        }
        String password = encoder.encode(user.getPassword());
        log.info("user почта {} ", user.getEmail());
        log.info("user role {} ", user.getRole());

        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return true;
    }
}