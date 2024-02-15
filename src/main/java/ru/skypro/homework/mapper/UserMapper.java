package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.users.*;
import ru.skypro.homework.model.UserModel;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper SAMPLE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "image", target = "image.path")
    UserModel toModelUser(UserDto dto);
    @Mapping(source = "image.path", target = "image")
    UserDto toDtoUser(UserModel user);


    UserModel toModelUser(UpdateUserDto dto);

    UpdateUserDto toDtoUpdateUser(UserModel user);


    @Mapping(source = "username", target = "email")
    UserModel toModelUser(LoginDto dto);

    @Mapping(source = "email", target = "username")
    LoginDto toDtoLogin(UserModel user);


    @Mapping(source = "username", target = "email")
    UserModel toModelUser(RegisterDto dto);

    @Mapping(source = "email", target = "username")
    RegisterDto toDtoRegister(UserModel user);


    @Mapping(source = "currentPassword", target = "password")
    UserModel toModelUser(NewPasswordDto dto);

    @Mapping(source = "password", target = "currentPassword")
    NewPasswordDto toDtoNewPassword(UserModel user);
}