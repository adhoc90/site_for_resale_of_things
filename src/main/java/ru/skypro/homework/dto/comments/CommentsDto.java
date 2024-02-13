package ru.skypro.homework.dto.comments;

import lombok.Data;

@Data
public class CommentsDto {

    private Integer count;
    private CommentDto results;
}