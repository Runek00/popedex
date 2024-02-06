package com.example.popedex.controllers;

import com.example.popedex.entities.User;
import com.example.popedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/new")
    String newUserInput() {
        return "new_user";
    }

    @PostMapping("/new")
    String addNewStatue(@RequestParam String login,
                        @RequestParam String password,
                        @RequestParam String repassword,
                        @RequestParam String email,
                        Model model) {
        User u = new User(null, login, email, LocalDateTime.now(), true);
        try {
            userService.addUser(u, password, repassword);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "new_user";
        }
        return "redirect:/";
    }
}
