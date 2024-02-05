package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPassword;
import ru.skypro.homework.dto.users.UpdateUser;
import ru.skypro.homework.dto.users.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.FileStorageService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;


    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<Void> setPassword(@RequestBody(required = false) NewPassword newPassword) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUserName = authentication != null ? authentication.getName() : null;
            if (loggedInUserName == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            ru.skypro.homework.model.User user = userRepository.findUserByEmail(loggedInUserName)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            if (newPassword == null || StringUtils.isEmpty(newPassword.getNewPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<User> getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUserName = authentication != null ? authentication.getName() : null;
            if (loggedInUserName == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            ru.skypro.homework.model.User user = userRepository.findUserByEmail(loggedInUserName)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            User userDto = userMapper.toDtoUser(user);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PatchMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody(required = false) UpdateUser updateUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUserName = authentication != null ? authentication.getName() : null;
            if (loggedInUserName == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            userRepository.findUserByEmail(loggedInUserName)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            ru.skypro.homework.model.User user;
            user = userMapper.toModelUser(updateUser);
            user = userRepository.save(user);
            UpdateUser updateUserDto = userMapper.toDtoUpdateUser(user);
            return ResponseEntity.ok(updateUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<String> updateImage(@RequestBody(required = false) MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String fileUrl = fileStorageService.saveFile(multipartFile);
            return ResponseEntity.ok(fileUrl);
        }
        return ResponseEntity.badRequest().build();
    }
}