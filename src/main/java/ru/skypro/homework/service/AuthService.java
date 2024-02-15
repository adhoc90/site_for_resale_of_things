package ru.skypro.homework.service;

import ru.skypro.homework.dto.users.RegisterDto;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterDto register);
}
