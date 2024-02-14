package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findUserByEmail(String email);
}
