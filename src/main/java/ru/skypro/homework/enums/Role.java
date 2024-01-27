package ru.skypro.homework.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("Пользователь"),
    ADMIN("Администратор");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}