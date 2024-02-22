package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Загрузка картинок")
public class ImageController {
    private final ImageService imageService;

    @GetMapping(value = "/images/{path}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            "image/*"
    })
    @Operation(summary = "получение картинки по пути")
    public ResponseEntity<byte[]> getImage(@PathVariable("path") String path) {
        log.info("Отправляем параметры в сервис, для получения байт картинки");
        byte[] imageBytes = Optional.ofNullable(imageService.getImageByPath(path)).orElseGet(() -> new byte[0]);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(imageBytes);

    }
}