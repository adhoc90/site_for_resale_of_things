package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.FileStorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.delete-dir}")
    private String deleteDir;


    @Override
    public byte[] getFileBytes(String filePath) {
        log.info("Пробуем получить и вернуть массив байтов картинки {}", filePath);
        Path path = Paths.get(uploadDir + "/images", filePath);
        if (Files.exists(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException("Не получилось прочитать байты", e);
            }
        } else {
            return new byte[0];
        }
    }

    @Override
    public void deleteFile(String filePath) {
        log.info("Пробуем удалить старую картинку {}", filePath);
        try {
            Path path = Paths.get(deleteDir, filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось удалить файл", e);
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            String fileName = generateFileName(file);
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            File targetFile = filePath.toFile();
            targetFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(file.getBytes());
            }
            return targetFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private String generateFileName(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFileName);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueFileName = UUID.randomUUID().toString();

        return timestamp + "_" + uniqueFileName + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }
}