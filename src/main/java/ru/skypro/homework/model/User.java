package ru.skypro.homework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skypro.homework.dto.enums.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role USER;
    private int password;
    private byte[] images;
}
