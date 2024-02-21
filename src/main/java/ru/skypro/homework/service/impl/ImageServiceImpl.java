package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileStorageServiceImpl fileStorageService;
    private final ImageRepository imageRepository;

    @Override
    public ImageModel saveMultipartFile(MultipartFile multipartFile) {
        log.info("Сохраняем и возвращаем изображение");
        ImageModel image = new ImageModel();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String savedFilePath = fileStorageService.saveFile(multipartFile);
            image.setPath(savedFilePath);
            return imageRepository.save(image);
        } else {
            throw new IllegalArgumentException("Пустое изображение не может быть сохранено!");
        }
    }

    @Override
    public ImageModel save(ImageModel image) {
        log.info("Сохраняем и возвращаем картинку");
        return imageRepository.save(image);
    }

    @Override
    public byte[] getImageByPath(String path) {
        log.info("Находим и возвращаем массив байтов картинки");
        return fileStorageService.getFileBytes(path);
    }


    @SneakyThrows
    @Override
    public byte[] download(String imagePath) {
        Path fullImagePath = Path.of(uploadDir, imagePath);
        return Files.exists(fullImagePath) ? Files.readAllBytes(fullImagePath) : new byte[]{};
    }
}
