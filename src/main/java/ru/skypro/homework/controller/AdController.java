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
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;

@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdController {

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<Ads> getAllAds() {
        Ads ads = new Ads(); // тут будет метод вызванный с сервиса который возвращает лист всех объявлений
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления")
    public ResponseEntity<Ad> addAd(@RequestBody(required = false) CreateOrUpdateAd properties,
                                    @RequestParam(name = "image") MultipartFile image) {
        Ad ad = new Ad();   // метод сервиса на добавление объявления
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ResponseEntity<ExtendedAd> getInfoAd(@PathVariable Integer id) {
        ExtendedAd extendedAd = new ExtendedAd(); // будет метод сервиса
        if (extendedAd == null) {
            return ResponseEntity.notFound().build();
        } else if (extendedAd.getPk() == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления")
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        HttpStatus status = HttpStatus.OK;          // будет метод сервиса который возвращает статус удаления объявления
        return ResponseEntity.status(status).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении")
    public ResponseEntity<Ad> updateInfoAd(@PathVariable Integer id,
                                           @RequestBody(required = false) CreateOrUpdateAd createOrUpdateAd) {
        Ad ad = new Ad();
        if (ad == null) {
            return ResponseEntity.notFound().build();
        } else if (ad.getPk() == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (ad.getPk() == -2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<Ads> getAds() {
        Ads ads = new Ads();
        if (ads.getCount() == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления")
    public ResponseEntity<String[]> updateImageAd(@PathVariable Integer id,
                                                  @RequestBody(required = false) MultipartFile multipartFile) {
        String[] strings = new String[10]; // если что-то не так, нулевой элемент массива будет содержать код ошибки. Пока тут заглушка
        if (strings[10].equals("403")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (strings[10].equals("401")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (strings[10].equals("404")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(strings);
    }
}