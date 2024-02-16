package com.example.popedex.controllers;

import com.example.popedex.entities.User;
import com.example.popedex.services.StatueService;
import com.example.popedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final StatueService statueService;

    @Autowired
    public UserController(UserService userService, StatueService statueService) {
        this.userService = userService;
        this.statueService = statueService;
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
    String showUser(Model model, Principal principal) {
        String name = principal.getName();
        User user = userService.getUser(name);
        int statues = statueService.countForUser(user.id());
        model.addAttribute("username", user.username());
        model.addAttribute("email", user.email());
        model.addAttribute("register_time", user.registerTime());
        model.addAttribute("statues", statues);
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
