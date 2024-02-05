package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper SAMPLE = Mappers.getMapper(UserMapper.class);


    User toModelUser(ru.skypro.homework.dto.users.User dto);
    ru.skypro.homework.dto.users.User toDtoUser(User user);


    User toModelUser(ru.skypro.homework.dto.users.UpdateUser dto);
    ru.skypro.homework.dto.users.UpdateUser toDtoUpdateUser(User user);


    @Mapping(source = "userName", target = "email")
    User toModelUser(ru.skypro.homework.dto.users.Login dto);
    @Mapping(source = "email", target = "userName")
    ru.skypro.homework.dto.users.Login toDtoLogin(User user);


    @Mapping(source = "userName", target = "email")
    User toModelUser(ru.skypro.homework.dto.users.Register dto);
    @Mapping(source = "email", target = "userName")
    ru.skypro.homework.dto.users.Register toDtoRegister(User user);


    @Mapping(source = "currentPassword", target = "password")
    User toModelUser(ru.skypro.homework.dto.users.NewPassword dto);
    @Mapping(source = "password", target = "currentPassword")
    ru.skypro.homework.dto.users.NewPassword toDtoNewPassword(User user);
}