package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.CommentModel;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentModel, Integer> {

    CommentModel getCommentByPk(Integer id);

    Optional<CommentModel> findByPk(Integer pk);

    List<CommentModel> findAllByAdPk(Integer pk);
}