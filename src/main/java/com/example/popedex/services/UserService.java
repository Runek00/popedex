package com.example.popedex.services;

import com.example.popedex.entities.User;
import com.example.popedex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User u, String password, String rePassword) throws IllegalArgumentException {
        if (!Objects.equals(password, rePassword)) {
            throw new IllegalArgumentException("password should be the same");
        }
        if(!userRepository.checkLoginUnique(u.login())) {
            throw new IllegalArgumentException("login should be unique");
        }
        userRepository.save(u);
        userRepository.updatePassword(u.id(), password);

    }
}
