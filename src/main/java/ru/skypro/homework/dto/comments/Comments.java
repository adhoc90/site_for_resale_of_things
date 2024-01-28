package ru.skypro.homework.dto.comments;

import lombok.Data;
import ru.skypro.homework.dto.comments.Comment;

@Data
public class Comments {

    private Integer count;
    private Comment results;
}
