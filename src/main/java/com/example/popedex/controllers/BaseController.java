package com.example.popedex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController {

    @RequestMapping("")
    public String index() {
        return "redirect:/statues";
    }

    @RequestMapping("/")
    public String index2() {
        return "redirect:/statues";
    }

    @RequestMapping("/secure")
    @ResponseBody
    public String secure() {
        return "This is secured";
    }

    @RequestMapping("/login")
    String login() {
        return "login";
    }
}
