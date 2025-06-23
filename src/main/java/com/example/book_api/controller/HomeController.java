package com.example.book_api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the book API";
    }

    @GetMapping("/home")
    public String welcome() {
        return "This is the /home page of the book API";
    }

    @GetMapping("/admin")
    public String homePage() {
        return "This is the /admin dashboard";
    }
}
