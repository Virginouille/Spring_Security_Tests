package com.example.book_api.securite;

import com.example.book_api.model.Book;
import com.example.book_api.model.User;
import com.example.book_api.repository.BookRepository;
import com.example.book_api.repository.UserRepository;
import com.example.book_api.service.RefreshTokenService;
import com.example.book_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RefreshTokenService refreshTokenService;

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

        bookRepository.saveAll(List.of(
                new Book("Book 1", "Author 1", "ISBN 1"),
                new Book("Book 2", "Author 2", "ISBN 2"),
                new Book("Book 3", "Author 3", "ISBN 3")
        ));

        refreshTokenService.generateRefreshToken("admin");
    }
}
