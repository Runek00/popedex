package com.example.popedex.services;

import com.example.popedex.entities.User;
import com.example.popedex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

    public User getUserFromPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return getUser(principal.getName());
        } else if (principal instanceof OAuth2AuthenticationToken) {
            String username = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("id").toString() + ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("login").toString();
            String visibleName = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("login").toString();
            String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email").toString();
            User baseUser = getUser(username);
            if (baseUser == null) {
                User newUser = new User(null, username, "-1", null, null, LocalDateTime.now(), true, true);
                baseUser = addUserToDb(newUser);
            }
            return new User(baseUser.id(), username, baseUser.password(), visibleName, email, baseUser.registerTime(), baseUser.enabled(), baseUser.fromOauth());
        } else {
            return null;
        }
    }

    @Transactional
    public void addUser(String username, String password, String email, LocalDateTime registerTime, String rePassword) throws IllegalArgumentException {
        if (!Objects.equals(password, rePassword)) {
            throw new IllegalArgumentException("password should be the same");
        }
        password = passwordEncoder.encode(password);
        User u = new User(null, username, password, email, registerTime, true, false);
        addUserToDb(u);
    }

    private User addUserToDb(User u) {
        if (!userRepository.checkLoginUnique(u.username())) {
            throw new IllegalArgumentException("username should be unique");
        }
        User output = userRepository.save(u);
        userRepository.addUserRole(u.username());
        return output;
    }

    public User getUser(String name) {
        return userRepository.findByUsername(name);
    }
}
