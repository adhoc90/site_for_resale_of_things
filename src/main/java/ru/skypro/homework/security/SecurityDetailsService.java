package ru.skypro.homework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
public class SecurityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public SecurityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return User.builder()
                .password(userRepository.findUserByEmail(email).get().getPassword())
                .username(userRepository.findUserByEmail(email).get().getEmail())
                .roles(userRepository.findUserByEmail(email).get().getRole().name())
                .build();

    }
}
