package com.example.popedex.controllers;

import com.example.popedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.Security;
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
    String addNewUser(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String repassword,
                      @RequestParam String email,
                      Model model) {
        try {
            userService.addUser(username, password, email, LocalDateTime.now(), true, repassword);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "new_user";
        }
        return "redirect:/login";
    }

    @GetMapping("/view")
    String eitUser(Model model, Principal principal) {
        var p = principal;
        // TODO
        return "view_user";

    }

    @GetMapping("/edit")
    String editUser(Model model, Principal principal) {
        // TODO
        return "edit_user";
    }

    @PostMapping("/edit")
    String saveUserChanges(@RequestParam String email) {
        // TODO
        return "redirect:/view";
    }

    @GetMapping("/")
    @ResponseBody
    String visibleUser() {
        return "secret!";
    }
}
