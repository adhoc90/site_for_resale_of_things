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
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdController {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto ads = new AdsDto(); // тут будет метод вызванный с сервиса который возвращает лист всех объявлений
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление объявления")
    public ResponseEntity<CreateOrUpdateAdDto> addAd(@RequestBody(required = false) CreateOrUpdateAdDto properties,
                                                     @RequestParam(name = "image") MultipartFile image) {
        // метод сервиса на добавление объявления
        int stub = 10;
        if (stub >= 10) {
            return ResponseEntity.status(HttpStatus.CREATED).body(properties);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение информации об объявлении")
    public ResponseEntity<ExtendedAdDto> getInfoAd(@PathVariable Integer id) {

        int stub = 10;
        ExtendedAdDto extendedAd = new ExtendedAdDto();
        if (stub < 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub > 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления")
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        int stub = 10;                 // будет метод сервиса который возвращает статус удаления объявления
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10 && stub > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (stub <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление информации об объявлении")
    public ResponseEntity<AdDto> updateInfoAd(@PathVariable Integer id,
                                              @RequestBody(required = false) CreateOrUpdateAdDto createOrUpdateAd) {
        AdDto stubObj = new AdDto(); /*объект-заглушка*/
        int stub = 10; /*заглушка*/
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10 && stub > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (stub <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stubObj);
    }

    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<AdsDto> getAds() {
        int stub = 10;
        AdsDto ads = new AdsDto();
        if (stub < 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление картинки объявления")
    public ResponseEntity<String[]> updateImageAd(@PathVariable Integer id,
                                                  @RequestBody(required = false) MultipartFile multipartFile) {
        int stub = 10;
        String[] strings = new String[10]; // Если что-то не так, нулевой элемент массива будет содержать код ошибки. Пока тут заглушка
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (stub < 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (stub < 10 && stub >= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(strings);
    }
}