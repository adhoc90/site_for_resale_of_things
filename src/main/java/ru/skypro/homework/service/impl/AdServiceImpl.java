package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdModel;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdMapper adMapper;
    private final FileStorageServiceImpl fileStorageService;

    /**
     * Получает список всех объявлений.
     *
     * @return Список всех объявлений.
     */
    @Override
    public AdsDto getAllAds() {
        log.info("Получаем и возвращаем список всех объявлений");
        List<ru.skypro.homework.model.AdModel> allAds = adRepository.findAll();
        List<AdDto> results = allAds.stream()
                .map(adMapper::toDtoAd)
                .collect(Collectors.toList());
        return new AdsDto(results.size(), results);
    }

    /**
     * Создает новое объявление.
     *
     * @param properties    Объект CreateOrUpdateAd с информацией об объявлении.
     * @param multipartFile Мультипарт-файл изображения для объявления.
     * @param email         Email пользователя, создающего объявление.
     * @return Созданное объявление.
     */
    @Override
    public CreateOrUpdateAdDto createAd(CreateOrUpdateAdDto properties, MultipartFile multipartFile, String email) {
        log.info("Создаем и возвращаем объект DTO объявление пользователя {}", email);
        AdModel entityAd = adMapper.toModelAd(properties);
        entityAd.setUser(userService.findUserByEmail(email));
        entityAd.setImage(imageService.saveMultipartFile(multipartFile));
        return adMapper.toDtoCreateOrUpdateAd(adRepository.save(entityAd));
    }

    /**
     * Получает информацию об объявлении по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return Объект AdDTO, представляющий информацию об объявлении.
     */
    @Override
    public ExtendedAdDto getAdInfo(Integer id) {
        log.info("Находим и возвращаем информацию объявления по id {}", id);
        return adMapper.toDtoExtendedAd(adRepository.findById(id).orElse(null));
    }

    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return true, если объявление успешно удалено, false в противном случае.
     */
    @Override
    public boolean deleteAd(Integer id) {
        log.info("Удаляем объявление по id {} и возвращаем boolean значение", id);
        return adRepository.findById(id)
                .map(ad -> {
                    fileStorageService.deleteFile(ad.getImage().getPath());
                    adRepository.delete(ad);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Обновляет информацию об объявлении.
     *
     * @param createOrUpdateAd Объект AdDTO с обновленными данными.
     * @param id               Идентификатор объявления.
     * @return Обновленное объявление.
     */
    @Override
    public AdDto updateAd(CreateOrUpdateAdDto createOrUpdateAd, Integer id) {
        log.info("Обновляем объявление по id {} и возвращаем объект DTO", id);
        return adRepository.findById(id)
                .map(ad -> {
                    ad.setPrice(createOrUpdateAd.getPrice());
                    ad.setTitle(createOrUpdateAd.getTitle());
                    ad.setDescription(createOrUpdateAd.getDescription());
                    return adMapper.toDtoAd(adRepository.save(ad));
                })
                .orElse(null);
    }

    /**
     * Находит объявление по его идентификатору.
     *
     * @param id Идентификатор объявления.
     * @return Объект AdDTO, представляющий найденное объявление.
     */
    @Override
    public AdModel findAdById(Integer id) {
        log.info("Находим и возвращаем объявление по id {}", id);
        return adRepository.findById(id).orElse(null);
    }

    /**
     * Получает список объявлений авторизованного пользователя.
     *
     * @param email Email авторизованного пользователя.
     * @return Список объявлений авторизованного пользователя.
     */
    @Override
    public AdsDto getAuthUserAds(String email) {
        log.info("Находим и возвращаем список объявлений авторизованного пользователя {}", email);
        List<AdModel> results = userService.findUserByEmail(email).getAds();
        List<AdDto> userAdDTOS = results.stream()
                .map(adMapper::toDtoAd)
                .collect(Collectors.toList());
        return new AdsDto(results.size(), userAdDTOS);
    }

    /**
     * Обновляет изображение объявления.
     *
     * @param id            Идентификатор объявления.
     * @param multipartFile Мультипарт-файл с новым изображением.
     * @return URL обновленного изображения.
     */
    @Override
    public String updateImage(Integer id, MultipartFile multipartFile) {
        log.info("Обновляем картинку объявления {}", id);
        String newImage = fileStorageService.saveFile(multipartFile);
        AdModel ad = findAdById(id);
        ImageModel image;
        if (ad != null && ad.getImage() != null) {
            image = ad.getImage();
            fileStorageService.deleteFile(image.getPath());
            image.setPath(newImage);
            log.info("Сохраняем и возвращаем ссылку в проекте на новую картинку {}", image.getPath());
            return imageService.save(image).getPath();
        }
        log.info("Картинка не найдена");
        return null;
    }

    /**
     * Проверяет, является ли пользователь автором объявления.
     *
     * @param email Email пользователя.
     * @param adPk  Идентификатор объявления.
     * @return true, если пользователь является автором объявления, false в противном случае.
     */
    public boolean isAuthorAd(String email, Integer adPk) {
        log.info("Проверяем, является ли пользователь {} автором объявления {}", email, adPk);
        UserModel user = userService.findUserByEmail(email);
        AdModel ad = adRepository.findById(adPk).orElse(null);
        log.info("Выполняем проверку и возвращаем boolean значение");
        return user != null && ad != null && ad.getUser().getId().equals(user.getId());
    }
}