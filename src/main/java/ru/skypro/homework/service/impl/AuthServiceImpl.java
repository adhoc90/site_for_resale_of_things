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

import java.io.ObjectOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        log.info("Зарегистрированный пользователь пытается войти");
        if (userRepository.findUserByEmail(userName).isEmpty()) {
            log.info("Такой пользователь отсутствует");
            return false;
        }

        log.info("userName исправен 1");
//        UserDetails userDetails = manager.loadUserByUsername(userName);
        log.info("userName исправен 2");
        return encoder.matches(password, userRepository.findUserByEmail(userName).get().getPassword());
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
        ru.skypro.homework.model.User savedUser = userRepository.save(user);
        userRepository.save(savedUser);

        return true;
    }
}


//    @Override
//    public boolean login(String userName, String password) {
//        if (userRepository.findUserByEmail(userName).isEmpty()) {
//            log.info("Такого пользователя не найдено");
//            return false;
//        }
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        return encoder.matches(password, userDetails.getPassword());
//    }


//    @Override
//    public boolean register(ru.skypro.homework.model.User user) {
//        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
//            log.info("Такой пользователь существует");
//            return false;
//        }
//        log.info("Начало создания пользователя {}", user.getPassword());
//        manager.createUser(
//                User.builder()
//                        .passwordEncoder(this.encoder::encode)
//                        .password(user.getPassword())
//                        .username(user.getEmail())
//                        .roles(user.getRole().name())
//                        .build());
//        userRepository.save(user);
//        return true;
//    }

