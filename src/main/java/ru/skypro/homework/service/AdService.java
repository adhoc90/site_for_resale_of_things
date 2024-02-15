package ru.skypro.homework.service;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.AdModel;

public interface AdService {
    /**
     * Получает список всех объявлений.
     *
     * @return Список всех объявлений.
     */
    AdsDto getAllAds();

    /**
     * Создает новое объявление.
     *
     * @param properties Объект CreateOrUpdateAd, содержащий данные для создания объявления.
     * @param image    Файл изображения, связанный с объявлением.
     * @param email    Email пользователя, создающего объявление.
     * @return Созданное объявление.
     */
    CreateOrUpdateAdDto createAd(CreateOrUpdateAdDto properties, MultipartFile image, String email);

    /**
     * Получает информацию об объявлении по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return Объект AdDTO, содержащий информацию об объявлении.
     */
    @Nullable
    ExtendedAdDto getAdInfo(Integer id);

    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return true, если объявление успешно удалено, иначе false.
     */
    @Nullable
    boolean deleteAd(Integer id);

    /**
     * Обновляет объявление.
     *
     * @param createOrUpdateAd Объект CreateOrUpdateAd, содержащий обновленные данные для объявления.
     * @param id       Идентификатор обновляемого объявления.
     * @return Обновленное объявление.
     */
    AdDto updateAd(CreateOrUpdateAdDto createOrUpdateAd, Integer id);

    /**
     * Находит объявление по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return Объект AdDTO, представляющий найденное объявление.
     */
    @Nullable
    AdModel findAdById(Integer id);

    /**
     * Получает список объявлений, созданных авторизованным пользователем.
     *
     * @param email Email авторизованного пользователя.
     * @return Список объявлений, созданных пользователем с указанным email.
     */
    AdsDto getAuthUserAds(String email);

    /**
     * Обновляет изображение, связанное с объявлением.
     *
     * @param id            Идентификатор объявления.
     * @param multipartFile Файл изображения для обновления.
     * @return URL обновленного изображения.
     */
    @Nullable
    String updateImage(Integer id, MultipartFile multipartFile);
}
