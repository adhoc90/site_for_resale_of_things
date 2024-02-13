package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final FileStorageServiceImpl fileStorageService;
    private final ImageRepository imageRepository;

    @Override
    public Image saveMultipartFile(MultipartFile multipartFile) {
        log.info("Сохраняем и возвращаем изображение");
        Image image = new Image();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String savedFilePath = fileStorageService.saveFile(multipartFile);
            image.setPath(savedFilePath);
            return imageRepository.save(image);
        } else {
            throw new IllegalArgumentException("Пустое изображение не может быть сохранено!");
        }
    }

    @Override
    public Image save(Image image) {
        log.info("Сохраняем и возвращаем картинку");
        return imageRepository.save(image);
    }

    @Override
    public byte[] getImageByPath(String path) {
        log.info("Находим и возвращаем массив байтов картинки");
        return fileStorageService.getFileBytes(path);
    }
}
