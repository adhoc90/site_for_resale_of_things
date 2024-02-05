package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

@Mapper
public interface CommentMapper {

    CommentMapper SAMPLE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorImage", target = "user.image")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    Comment toModelComment(ru.skypro.homework.dto.comments.Comment dto);

    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    ru.skypro.homework.dto.comments.Comment toCommentDto(User user, Comment comment);

    Comment toModelComment(ru.skypro.homework.dto.comments.Comments dto);
    ru.skypro.homework.dto.comments.Comments toCommentsDto(Comment comment);

    Comment toModelComment(ru.skypro.homework.dto.comments.CreateOrUpdateComment dto);
    ru.skypro.homework.dto.comments.CreateOrUpdateComment toCreateOrUpdateComment(Comment comment);
}