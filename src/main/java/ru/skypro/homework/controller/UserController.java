package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для работы с пользователями.
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    /**
     * Запрос на обновление пароля авторизованного пользователя
     *
     * @param newPassword    данные нового пароля
     * @param authentication данные для аутентификации
     * @return статс ответа в зависимости от результата изменения пароля
     */
    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<String> setPassword(@RequestBody(required = false) NewPasswordDto newPassword,
                                              Authentication authentication) {
        log.info("Отправляем данные в сервис для обновления пароля");
        if (!authentication.isAuthenticated()) {
            log.info("Пользователь не прошёл аутентификацию {} ", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!userService.setPassword(newPassword, authentication)) {
            log.info("не удалось изменить пароль {} ", authentication.getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Обновление пароля прошло успешно {} ", authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Запрос на получение информации о пользователе
     *
     * @param authentication аутентификация пользователя
     * @return статус ответа
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        log.info("Запрос, на получение информации авторизованного пользователя");
        UserDto userInfo = userService.getUserInformation(authentication);
        if (userInfo == null) {
            log.info("Ошибка получения информации {} ", authentication.getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Информация успешно получена {} ", authentication.getName());
        return ResponseEntity.ok(userInfo);
    }


    /**
     * Запрос на конечную точку обновление информации пользователя
     *
     * @param userDto        данные пользователя
     * @param authentication аутентификация пользователя
     * @return статус ответа
     */
    @PatchMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody(required = false) UpdateUserDto userDto,
                                                    Authentication authentication) {
        log.info("Запрос на обновление информации авторизованного пользователя");
        UpdateUserDto newInfo = userService.updateUserInfo(userDto, authentication);
        if (newInfo == null) {
            log.info("Ошибка обновления информации {} ", authentication.getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Успешное обновление информации {} ", authentication.getName());
        return ResponseEntity.ok(newInfo);
    }

    /**
     * Запрос на обновление аватара авторизованного пользователя
     *
     * @param multipartFile  картинка пользователя
     * @param authentication аутентификация
     * @return статус ответа
     */
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<String> updateImage(@RequestBody(required = false) MultipartFile multipartFile,
                                              Authentication authentication) {
        log.info("Запрос на обновление аватара, авторизованного пользователя {} ", authentication.getName());
        String newImage = userService.updateUserImage(multipartFile, authentication);
        if (newImage == null) {
            log.info("Ошибка обновления аватара {} ", authentication.getName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(newImage);
    }
}