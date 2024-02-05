package com.example.popedex.controllers;

import com.example.popedex.entities.User;
import com.example.popedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    String addNewStatue(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam String rePassword,
                        @RequestParam String email) {
        User u = new User(null, userName, email, LocalDateTime.now(), true);
        userService.addUser(u, password, rePassword);
        return "redirect:/";
    }
}
