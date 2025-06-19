package com.example.book_api.securite;

import com.example.book_api.model.User;
import com.example.book_api.repository.BookRepository;
import com.example.book_api.repository.UserRepository;
import com.example.book_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userService.getUserByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin");
            userService.createUser(adminUser);
            System.out.println("User created with an encode password");
        } else {
            System.out.println("User already exists");
        };
    }
}
