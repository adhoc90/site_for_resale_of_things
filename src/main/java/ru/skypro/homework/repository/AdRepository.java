package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdModel;

import java.util.List;

public interface AdRepository extends JpaRepository<AdModel, Integer> {

    AdModel getAdByPk(Integer id);
}
