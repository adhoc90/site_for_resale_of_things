package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageModel;

public interface ImageService {

    ImageModel saveMultipartFile(MultipartFile multipartFile);

    ImageModel save(ImageModel image);

    byte[] getImageByPath(String path);

    /**
     * Скачивание изображения
     *
     * @param imagePath Путь к изображению
     * @return Массив байт
     */
    byte[] download(String imagePath);
}