package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.model.UserModel;


public interface UserService {

    boolean setPassword(NewPasswordDto passwordDto, Authentication authentication);

    UserDto getUserInformation(Authentication authentication);

    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication);

    String updateUserImage(MultipartFile multipartFile, Authentication authentication);

    UserModel findUserByEmail(String email);
}