package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.model.User;

@Mapper
public abstract class UserMapper {

    public abstract UserDto convertToDto(User entity);
}
