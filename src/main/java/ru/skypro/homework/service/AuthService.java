package ru.skypro.homework.service;

import ru.skypro.homework.model.UserModel;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(UserModel user);
}
