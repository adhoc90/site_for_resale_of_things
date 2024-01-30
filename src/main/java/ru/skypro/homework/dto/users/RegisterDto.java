package ru.skypro.homework.dto.users;

import lombok.Data;
import ru.skypro.homework.enums.Role;

@Data
public class RegisterDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}