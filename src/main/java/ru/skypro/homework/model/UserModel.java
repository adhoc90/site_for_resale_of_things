package ru.skypro.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.enums.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;


@Entity(name = "users")
@NoArgsConstructor
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;

    @Enumerated(STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageModel image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdModel> ads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel user = (UserModel) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}