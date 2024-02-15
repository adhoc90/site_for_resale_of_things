package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.AdModel;
import ru.skypro.homework.model.CommentModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdService adService;
    private final UserService userService;
    private final CommentMapper commentMapper;


    /**
     * Получает список всех комментариев для объявления с указанным идентификатором.
     *
     * @param id Идентификатор объявления.
     * @return Список всех комментариев для указанного объявления.
     */
    @Override
    public CommentsDto getAllAdComments(Integer id) {
        log.info("Получаем и возвращаем список всех комментариев");

        List<CommentModel> comments = commentRepository.findAllByAdPk(id);
        List<CommentDto> results = comments.stream()
                .map(commentMapper::toDtoComment)
                .collect(Collectors.toList());
        return new CommentsDto(results.size(), results);
    }

    /**
     * Создает новый комментарий для указанного объявления и пользователя.
     *
     * @param text  Объект CreateOrUpdateComment с информацией о новом комментарии.
     * @param pk    Идентификатор объявления.
     * @param email Email пользователя, создающего комментарий.
     * @return Созданный комментарий или null, если объявление не найдено.
     */
    @Override
    @Transactional
    public CommentDto createComment(CreateOrUpdateCommentDto text, Integer pk, String email) {
        log.info("Создаем комментарий пользователя {} и возвращаем объект DTO", email);
        AdModel ad = adService.findAdById(pk);
        UserModel user = userService.findUserByEmail(email);
        if (ad != null && user != null) {
            CommentModel newComment = commentMapper.toModelComment(text);
            newComment.setUser(user);
            newComment.setAd(ad);
            newComment.setCreatedAt(System.currentTimeMillis());
            return commentMapper.toDtoComment(commentRepository.save(newComment));
        } else {
            return null;
        }
    }

    /**
     * Удаляет комментарий по указанному идентификатору.
     *
     * @param adId      Идентификатор объявления, к которому относится комментарий.
     * @param commentId Идентификатор комментария.
     * @return true, если комментарий успешно удален, false в противном случае.
     */
    @Override
    @Transactional
    public boolean deleteComment(Integer adId, Integer commentId) {
        log.info("Удаляем комментарий по id {} и возвращаем boolean значение", commentId);
        return Optional.ofNullable(adService.findAdById(adId))
                .map(ad -> {
                    commentRepository.deleteById(commentId);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Обновляет комментарий по указанному идентификатору.
     *
     * @param createOrUpdateComment Объект CommentDTO с обновленной информацией о комментарии.
     * @param adId                  Идентификатор объявления, к которому относится комментарий.
     * @param commentId             Идентификатор комментария.
     * @param email                 Email пользователя, обновляющего комментарий.
     * @return Обновленный комментарий или null, если комментарий не найден или не относится к указанному объявлению.
     */
    @Override
    @Transactional
    public CommentDto updateComment(CreateOrUpdateCommentDto createOrUpdateComment, Integer adId, Integer commentId, String email) {
        log.info("Обновляем комментарий по id {} пользователя {} и возвращаем объект DTO", commentId, email);
        return commentRepository.findById(commentId)
                .filter(comment -> comment.getAd().equals(adService.findAdById(adId)))
                .map(comment -> {
                    comment.setText(createOrUpdateComment.getText());
                    return commentMapper.toDtoComment(commentRepository.save(comment));
                })
                .orElseGet(() -> {
                    return null;
                });
    }

    /**
     * Проверяет, является ли указанный пользователь автором комментария.
     *
     * @param email     Email пользователя.
     * @param commentPk Идентификатор комментария.
     * @return true, если пользователь является автором комментария, false в противном случае.
     */
    public boolean isAuthorComment(String email, Integer commentPk) {
        log.info("Проверяем, является ли {} автором комментария {} и возвращаем boolean значение", email, commentPk);
        return commentRepository.findById(commentPk)
                .map(comment -> {
                    return comment.getUser().equals(userService.findUserByEmail(email));
                })
                .orElse(false);
    }
}