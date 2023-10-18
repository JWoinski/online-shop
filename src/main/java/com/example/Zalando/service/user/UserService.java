package com.example.Zalando.service.user;

import com.example.Zalando.model.User;
import com.example.Zalando.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUserToDB(User user) {
        encodeAndSetPassword(user);
        userRepository.save(user);
    }

    public void encodeAndSetPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public User getCurrentLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User principal1 = (User) principal;
        return principal1;
    }
}