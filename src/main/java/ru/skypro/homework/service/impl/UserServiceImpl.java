package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
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
    public boolean setPassword(NewPasswordDto passwordDto, Authentication authentication) {
        log.info("Запрос на изменение пароля");
        User user;
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
        User user;
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
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication) {
        log.info("Запрос на обновление информации об пользователе");
        User user;
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
    public String updateUserImage(MultipartFile multipartFile, Authentication authentication) {
        log.info("Запрос на обновление автара");
        User user;
        try {
            user = userRepository.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован"));
        } catch (UsernameNotFoundException e) {
            log.info("Пользователь не зарегистрирован");
            return null;
        }
        String newImage = fileStorageService.saveFile(multipartFile);
        Image image;
        if (user.getImage() != null) {
            log.info("аватарка у пользователя была");
            image = user.getImage();
            fileStorageService.deleteFile(image.getPath());
        } else {
            log.info("аватарки у пользователя не было");
            image = new Image();
            user.setImage(image);
        }
        image.setPath(newImage);
        return imageService.save(image).getPath();
    }
}