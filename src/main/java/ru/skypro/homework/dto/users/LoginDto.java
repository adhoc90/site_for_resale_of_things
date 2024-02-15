package ru.skypro.homework.dto.users;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class LoginDto {

    @Size(min = 8, max = 16)
    private String password;
    @Size(min = 4, max = 32)
    private String username;
}