package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

import static org.springframework.http.MediaType.*;

@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdController {

    private final AdService adService;

    /**
     * Получение всех объявлений.
     *
     * @return ResponseEntity содержит список всех объявлений.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDto> getAllAds() {
        log.info("Отправляем запрос в сервис для получения всех объявлений");
        return ResponseEntity.ok(adService.getAllAds());
    }

    /**
     * Создание нового объявления
     * @param properties передача характеристики, пользователем
     * @param image передача картинки
     * @param authentication userName пользователя
     * @return статус ответа в зависимости от результата
     */
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление объявления")
    public ResponseEntity<CreateOrUpdateAdDto> createAd(@RequestPart CreateOrUpdateAdDto properties,
                                                        @RequestPart("image") MultipartFile image,
                                                        Authentication authentication) {
        log.info("Отправляем параметры в сервис для создания объявления пользователя {}", authentication.getName());
        CreateOrUpdateAdDto adDTO = adService.createAd(properties, image, authentication.getName());
        return adDTO != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(adDTO) :
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    /**
     * Получение расширенной информации объявления.
     *
     * @param id Идентификатор объявления.
     * @return ResponseEntity содержит расширенный набор параметров объявления.
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение информации об объявлении")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable("id") Integer id) {
        log.info("Отправляем параметры в сервис для получения информации объявления {}", id);
        return adService.getAdInfo(id) != null ?
                ResponseEntity.ok(adService.getAdInfo(id)) :
                ResponseEntity.notFound().build();
    }

    /**
     * Удаление объявления.
     *
     * @param id Идентификатор удаляемого объявления.
     * @return ResponseEntity содержит код статуса ответа (
     * HttpStatus.NO_CONTENT при успешном удалении, HttpStatus.NOT_FOUND при неудачном удалении).
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.getName(), #id)")
    @Operation(summary = "Удаление объявления")
    public ResponseEntity<Void> deleteAd(@PathVariable("id") Integer id) {
        log.info("Отправляем параметры в сервис для удаления объявления {}", id);
        return adService.deleteAd(id) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Обновление информации объявления.
     *
     * @param id               Идентификатор объявления.
     * @param createOrUpdateAd Обновленная версия объявления.
     * @return ResponseEntity содержит измененное объявление.
     */
    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.getName(), #id)")
    @Operation(summary = "Обновление информации об объявлении")
    public ResponseEntity<AdDto> updateAdInfo(@PathVariable("id") Integer id,
                                              @RequestBody(required = false) CreateOrUpdateAdDto createOrUpdateAd) {
        log.info("Отправляем параметры в сервис для обновления объявления {}", id);
        AdDto adDTO = adService.updateAd(createOrUpdateAd, id);
        return adDTO != null ?
                ResponseEntity.ok(adDTO) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Получение объявлений авторизованного пользователя.
     *
     * @param authentication Данные авторизации пользователя.
     * @return ResponseEntity содержит список объявлений пользователя.
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<AdsDto> getAuthUserAds(Authentication authentication) {
        log.info("Отправляем параметры в сервис для получения объявлений пользователя {}", authentication.getName());
        return ResponseEntity.ok(adService.getAuthUserAds(authentication.getName()));
    }

    /**
     * Обновление картинки объявления.
     *
     * @param id    Идентификатор объявления, к которому относится картинка.
     * @param image Новая картинка объявления.
     * @return ResponseEntity содержит новую картинку объявления или код статуса ответа при неудаче
     * (HttpStatus.NOT_FOUND).
     */
    @PatchMapping(value = "/{id}/image", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_OCTET_STREAM_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.getName(), #id)")
    @Operation(summary = "Обновление картинки объявления")
    public ResponseEntity<String> updateAdPhoto(@PathVariable("id") Integer id,
                                                @RequestPart("image") MultipartFile image) {
        log.info("Отправляем параметры в сервис для замены старой картинки объявления {}", id);
        String newImage = adService.updateImage(id, image);
        return newImage != null ?
                ResponseEntity.ok(newImage) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> getAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(adService.getImage(id));
    }
}