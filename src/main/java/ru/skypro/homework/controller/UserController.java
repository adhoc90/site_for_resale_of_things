package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPassword;
import ru.skypro.homework.dto.users.UpdateUser;
import ru.skypro.homework.dto.users.User;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {


    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.status(HttpStatus.OK).build(); // тут будет вызван метод сервиса, который вернет соответствующий статус (200, 401, 403)
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<User> getUser(@RequestParam("userId") Integer userId) {
        User user = new User();
//        здесь будет метод сервиса, который проверяет авторизован пользователь или нет и вернёт соответствующий статус
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        UpdateUser user = new UpdateUser();
//        здесь будет метод сервиса, который вернёт статус обновлённого пользователя
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<Void> updateImage(@RequestBody MultipartFile multipartFile) {
        HttpStatus httpStatus = HttpStatus.OK; // тут будет метод вызванный с сервиса который вернет соответствующий статус (200, 401)
        return ResponseEntity.status(httpStatus).build();
    }
}
