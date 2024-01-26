package ru.skypro.homework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "comment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Comment {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    private int author;
    private int createdAt;
    private String text;
    private String authorImage;
    private String authorFirstName;

}
