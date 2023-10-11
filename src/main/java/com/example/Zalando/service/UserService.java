package com.example.Zalando.service;

import com.example.Zalando.model.User;
import com.example.Zalando.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUserToDB(User user) {
        userRepository.save(user);
    }

    public Optional<User> loginUser(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}