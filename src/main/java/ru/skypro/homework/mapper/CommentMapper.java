package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

@Mapper
public interface CommentMapper {

    CommentMapper SAMPLE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorImage", target = "user.image")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    Comment toModelComment(CommentDto dto);

    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    CommentDto toCommentDto(User user, Comment comment);

    Comment toModelComment(CommentsDto dto);
    CommentsDto toCommentsDto(Comment comment);

    Comment toModelComment(CreateOrUpdateCommentDto dto);
    CreateOrUpdateCommentDto toCreateOrUpdateComment(Comment comment);
}