package ru.skypro.homework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ad")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Ad {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    private int price;
    private int author;
    private String authorFirstName;
    private String authorLastName;
    private String title;
    private String description;
    private String email;
    private String phone;
    private String image;
}
