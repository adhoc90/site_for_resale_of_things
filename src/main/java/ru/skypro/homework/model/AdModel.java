package ru.skypro.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity(name = "ads")
@NoArgsConstructor
@Data
public class AdModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    private Integer price;
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageModel image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;
}