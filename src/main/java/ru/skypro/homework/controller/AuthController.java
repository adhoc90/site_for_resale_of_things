package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.users.LoginDto;
import ru.skypro.homework.dto.users.RegisterDto;
import ru.skypro.homework.service.AuthService;

import static ru.skypro.homework.mapper.UserMapper.SAMPLE;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Авторизация")
    public ResponseEntity<?> login(@RequestBody(required = false) LoginDto login) {
        log.info("поступил запрос на вход от пользователя " + login.getUsername());
        if (authService.login(SAMPLE.toModelUser(login).getEmail(), SAMPLE.toModelUser(login).getPassword())) {
            log.info("пользователь " + login.getUsername() + " вошел успешно");
            return ResponseEntity.ok().build();
        } else {
            log.info("пользователь " + login.getUsername() + " не авторизован");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация")
    public ResponseEntity<?> register(@RequestBody(required = false) RegisterDto register) {
        log.info("поступил запрос на регистрацию");
        if (authService.register(SAMPLE.toModelUser(register))) {
            log.info("регистрация прошла успешно");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            log.info("неправильный запрос на регистрацию");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}