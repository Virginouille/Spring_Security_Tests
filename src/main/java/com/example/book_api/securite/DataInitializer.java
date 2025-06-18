package com.example.book_api.securite;

import com.example.book_api.model.User;
import com.example.book_api.repository.BookRepository;
import com.example.book_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("admin", "admin"));
    }

}
