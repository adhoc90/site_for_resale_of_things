package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.users.UserDto;
import ru.skypro.homework.model.User;

@Service
public abstract class UserMapper {

    public abstract UserDto convertToDto(User entity);
}
