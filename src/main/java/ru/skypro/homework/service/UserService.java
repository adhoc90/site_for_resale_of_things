package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UserDto;

import javax.transaction.Transactional;

public interface UserService {

    @Transactional
    boolean setPassword(NewPasswordDto passwordDto, Authentication authentication);

    UserDto getUserInformation(Authentication authentication);

    @Transactional
    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication);

    @Transactional
    String updateUserImage(MultipartFile multipartFile, Authentication authentication);
}