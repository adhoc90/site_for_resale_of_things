package ru.skypro.homework.dto.comments;

import lombok.Data;

import java.util.List;

@Data
public class CommentsDto {

    private Integer count;
    private List<CommentDto> results;

    public CommentsDto() {
    }

    public CommentsDto(Integer count, List<CommentDto> results) {
        this.count = count;
        this.results = results;
    }
}