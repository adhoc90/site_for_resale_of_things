package ru.skypro.homework.service;

import ru.skypro.homework.dto.users.NewPasswordDto;
import ru.skypro.homework.dto.users.UserDto;

public interface UserService {

    NewPasswordDto updatePassword();

    UserDto getInfoAboutAuthorizedUser();



}
