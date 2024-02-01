package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity(name = "ads")
@NoArgsConstructor
@Data

public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer pk;
    private Integer price;
    private String title;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
