package ru.skypro.homework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.security.AuthUserDetails;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class SecurityDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public SecurityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional.ofNullable(username);
        User user = userRepository.findUserByEmail(username).orElseThrow();
        ru.skypro.homework.dto.users.User user1 = UserMapper.SAMPLE.toDtoUser(user);
        UserDetails userDetails = new AuthUserDetails(user1);
        Authentication authentication;

        return userDetails;
    }
}
