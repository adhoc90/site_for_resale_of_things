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

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@Slf4j
@Tag(name = "Комментарии")
public class CommentController {

    @GetMapping("{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<Comments> getComment(@PathVariable("id") Integer id) {
        Comments comments = new Comments();
        if (comments.getCount() == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (comments.getCount() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping("{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<Comment> addCommentToAd(@PathVariable("id") Integer id,
                                                  @RequestBody(required = false) CreateOrUpdateComment text) {
        Comment comment = new Comment();
        if (comment.getPk() == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") String adId,
                                              @PathVariable("commentId") String commentId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария")
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId,
                                                 @RequestBody(required = false) CreateOrUpdateComment newComment) {
        Comment comment = new Comment();
        if (comment.getPk() == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (comment.getPk() == -2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
