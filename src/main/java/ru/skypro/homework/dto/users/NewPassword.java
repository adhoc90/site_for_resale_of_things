package ru.skypro.homework.dto.users;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewPassword {

    @Size(min = 8, max = 16)
    private String currentPassword;
    @Size(min = 8, max = 16)
    private String newPassword;
}