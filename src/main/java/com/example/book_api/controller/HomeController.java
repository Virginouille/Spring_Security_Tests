package com.example.book_api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Welcome to the book API";
    }

    @GetMapping("/home")
    @ResponseBody
    public String welcome() {
        return "This is the /home page of the book API";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String homePage() {
        return "This is the /admin dashboard";
    }
}
