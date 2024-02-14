package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.mapper.UserMapper.SAMPLE;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageServiceImpl fileStorageService;
    private final ImageService imageService;

    @Override
    @Transactional
    public boolean setPassword(NewPasswordDto passwordDto, Authentication authentication) {
        log.info("Запрос на изменение пароля");
        UserModel user;
        try {
            user = userRepository.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован"));
        } catch (UsernameNotFoundException e) {
            log.info("Пользователь не зарегистрирован");
            return false;
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getUserInformation(Authentication authentication) {
        log.info("Запрос на получение информации пользователя");
        UserModel user;
        try {
            user = userRepository.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован"));
        } catch (UsernameNotFoundException e) {
            log.info("Пользователь не зарегистрирован");
            return null;
        }
        return SAMPLE.toDtoUser(user);
    }

    @Override
    @Transactional
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication) {
        log.info("Запрос на обновление информации об пользователе");
        UserModel user;
        try {
            user = userRepository.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован"));
        } catch (UsernameNotFoundException e) {
            log.info("Пользователь не зарегистрирован");
            return null;
        }
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return updateUserDto;
    }

    @Override
    @Transactional
    public String updateUserImage(MultipartFile multipartFile, Authentication authentication) {
        log.info("Запрос на обновление автара");
        UserModel user;
        try {
            user = userRepository.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован"));
        } catch (UsernameNotFoundException e) {
            log.info("Пользователь не зарегистрирован");
            return null;
        }
        String newImage = fileStorageService.saveFile(multipartFile);
        ImageModel image;
        if (user.getImage() != null) {
            log.info("аватарка у пользователя была");
            image = user.getImage();
            fileStorageService.deleteFile(image.getPath());
        } else {
            log.info("аватарки у пользователя не было");
            image = new ImageModel();
            user.setImage(image);
        }
        image.setPath(newImage);
        return imageService.save(image).getPath();
    }

    @Override
    @Transactional
    public UserModel findUserByEmail(String email) {
        log.info("Находим и возвращаем пользователя по email {}", email);
        return userRepository.findUserByEmail(email).orElse(null);
    }
}