package com.example.popedex.services;

import com.example.popedex.entities.User;
import com.example.popedex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void addUser(String username, String password, String email, LocalDateTime registerTime, boolean enabled, String rePassword) throws IllegalArgumentException {
        if (!Objects.equals(password, rePassword)) {
            throw new IllegalArgumentException("password should be the same");
        }
        password = passwordEncoder.encode(password);
        User u = new User(null, username, password, email, registerTime, enabled);
        if (!userRepository.checkLoginUnique(u.username())) {
            throw new IllegalArgumentException("username should be unique");
        }
        userRepository.save(u);

    }
}
