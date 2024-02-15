package ru.skypro.homework.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageModel;

public interface ImageService {

    @Transactional
    ImageModel saveMultipartFile(MultipartFile multipartFile);

    @Transactional
    ImageModel save(ImageModel image);

    byte[] getImageByPath(String path);
}