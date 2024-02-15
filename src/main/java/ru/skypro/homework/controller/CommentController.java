package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@Slf4j
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentService commentService;

    /**
     * Получает комментарии для указанного объявления.
     *
     * @param id Идентификатор объявления.
     * @return ответ с комментариями объявления.
     */
    @GetMapping(value = "/{id}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<CommentsDto> getAdComments(@PathVariable("id") Integer id) {
        log.info("Отправляем параметры в сервис для получения комментариев объявления {}", id);
        return ResponseEntity.ok(commentService.getAllAdComments(id));
    }

    /**
     * Создает комментарий для указанного объявления.
     *
     * @param id             Идентификатор объявления.
     * @param text           Текст комментария.
     * @param authentication Аутентификационные данные пользователя.
     * @return ответ с созданным комментарием.
     */
    @PostMapping(value = "/{id}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<CommentDto> createAdComment(@PathVariable("id") Integer id,
                                                      @RequestBody(required = false) CreateOrUpdateCommentDto text,
                                                      Authentication authentication) {
        log.info("Отправляем параметры в сервис для создания комментария пользователя {}", authentication.getName());
        CommentDto commentDTO = commentService.createComment(text, id, authentication.getName());
        return
                commentDTO != null ?
                        ResponseEntity.status(HttpStatus.CREATED).body(commentDTO) :
                        ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    /**
     * Удаляет комментарий для указанного объявления.
     *
     * @param adId           Идентификатор объявления.
     * @param commentId      Идентификатор комментария.
     * @param authentication Аутентификационные данные пользователя.
     * @return ответ с кодом состояния в зависимости от результата удаления.
     */
    @DeleteMapping(value = "/{adId}/comments/{commentId}")
    @PreAuthorize(value = "hasRole('ADMIN') or @commentServiceImpl.isAuthorComment(authentication.getName(), #commentId)")
    @Operation(summary = "Удаление комментария")
    public ResponseEntity<Void> deleteAdComment(@PathVariable("adId") Integer adId,
                                                @PathVariable("commentId") Integer commentId,
                                                Authentication authentication) {
        log.info("Отправляем параметры в сервис для удаления комментария {} объявления {} пользователя {}",
                commentId, adId, authentication.getName());
        return commentService.deleteComment(adId, commentId) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Обновляет комментарий для указанного объявления.
     *
     * @param adId                  Идентификатор объявления.
     * @param commentId             Идентификатор комментария.
     * @param createOrUpdateComment Данные для обновления комментария.
     * @param authentication        Аутентификационные данные пользователя.
     * @return ответ с обновленным комментарием
     */
    @PatchMapping(value = "/{adId}/comments/{commentId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or @commentServiceImpl.isAuthorComment(authentication.getName(), #commentId)")
    @Operation(summary = "Обновление комментария")
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("adId") Integer adId,
                                                      @PathVariable("commentId") Integer commentId,
                                                      @RequestBody(required = false) CreateOrUpdateCommentDto createOrUpdateComment,
                                                      Authentication authentication) {
        log.info("Отправляем параметры в сервис для изменения комментария объявления {}", adId);
        CommentDto commentDTO = commentService.updateComment(createOrUpdateComment, adId, commentId, authentication.getName());
        return commentDTO != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(commentDTO) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}