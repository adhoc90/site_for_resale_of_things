package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.CommentModel;

@Mapper(componentModel = "spring")
public interface CommentMapper {

//    CommentMapper SAMPLE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "authorImage", target = "user.image.path")
    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    CommentModel toModelComment(CommentDto dto);

    @Mapping(source = "user.image.path", target = "authorImage")
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    CommentDto toDtoComment(CommentModel comment);

    CommentModel toModelComment(CommentsDto dto);

    CommentsDto toCommentsDto(CommentModel comment);

    CommentModel toModelComment(CreateOrUpdateCommentDto dto);

    CreateOrUpdateCommentDto toCreateOrUpdateComment(CommentModel comment);
}