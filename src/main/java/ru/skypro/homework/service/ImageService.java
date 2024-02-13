package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

public interface ImageService {

    Image saveMultipartFile(MultipartFile multipartFile);

    Image save(Image image);

    byte[] getImageByPath(String path);
}