package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.Comment;
import ru.skypro.homework.dto.comments.Comments;
import ru.skypro.homework.dto.comments.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@Slf4j
@Tag(name = "Комментарии")
public class CommentController {

//    private final CommentMapper commentMapper;

    @GetMapping(value = "{id}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<Comments> getComment(@PathVariable("id") Integer id) {
        Comments comments = new Comments();
        int stub = 10; /*заглушка*/
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping(value = "{id}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<Comment> addCommentToAd(@PathVariable("id") Integer id,
                                                  @RequestBody(required = false) CreateOrUpdateComment text) {
        Comment comment = new Comment();
        int stub = 10; /*заглушка*/
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") String adId,
                                              @PathVariable("commentId") String commentId) {
        int stub = 10; /*заглушка*/
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10 && stub > 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (stub <= 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(value = "{adId}/comments/{commentId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление комментария")
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId,
                                                 @RequestBody(required = false) CreateOrUpdateComment newComment) {
        Comment comment = new Comment();
        int stub = 10;
        if (stub > 10) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (stub < 10 && stub > 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (stub <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comment);
    }
}