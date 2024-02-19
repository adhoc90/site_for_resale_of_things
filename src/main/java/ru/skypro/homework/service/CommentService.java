package ru.skypro.homework.service;

import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;

public interface CommentService {


    /**
     * Получает список всех комментариев для объявления с указанным идентификатором.
     *
     * @param id Идентификатор объявления.
     * @return Список всех комментариев для указанного объявления.
     */
    CommentsDto getAllAdComments(Integer id);

    /**
     * Создает новый комментарий к объявлению.
     *
     * @param text  Объект CreateOrUpdateComment, содержащий данные для создания комментария.
     * @param pk    Идентификатор объявления, к которому создается комментарий.
     * @param email Email пользователя, создающего комментарий.
     * @return Созданный комментарий.
     */
    CommentDto createComment(CreateOrUpdateCommentDto text, Integer pk, String email);

    /**
     * Удаляет комментарий к объявлению.
     *
     * @param adId      Идентификатор объявления, к которому относится комментарий.
     * @param commentId Идентификатор удаляемого комментария.
     * @return true, если комментарий успешно удален, иначе false.
     */
    boolean deleteComment(Integer adId, Integer commentId);

    /**
     * Обновляет комментарий к объявлению.
     *
     * @param text      Объект CreateOrUpdateComment, содержащий обновленные данные для комментария.
     * @param adId      Идентификатор объявления, к которому относится комментарий.
     * @param commentId Идентификатор обновляемого комментария.
     * @param email     Email пользователя, обновляющего комментарий.
     * @return Обновленный комментарий.
     */
    CommentDto updateComment(CreateOrUpdateCommentDto text, Integer adId, Integer commentId, String email);
}