package ru.skypro.homework.service;

import ru.skypro.homework.dto.users.NewPassword;
import ru.skypro.homework.dto.users.User;

public interface UserService {

    NewPassword updatePassword();

    User getInfoAboutAuthorizedUser();



}
