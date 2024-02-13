package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.users.*;
import ru.skypro.homework.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper SAMPLE = Mappers.getMapper(UserMapper.class);


    User toModelUser(UserDto dto);
    UserDto toDtoUser(User user);


    User toModelUser(UpdateUserDto dto);
    UpdateUserDto toDtoUpdateUser(User user);


    @Mapping(source = "username", target = "email")
    User toModelUser(LoginDto dto);
    @Mapping(source = "email", target = "username")
    LoginDto toDtoLogin(User user);


    @Mapping(source = "username", target = "email")
    User toModelUser(RegisterDto dto);
    @Mapping(source = "email", target = "username")
    RegisterDto toDtoRegister(User user);


    @Mapping(source = "currentPassword", target = "password")
    User toModelUser(NewPasswordDto dto);
    @Mapping(source = "password", target = "currentPassword")
    NewPasswordDto toDtoNewPassword(User user);
}