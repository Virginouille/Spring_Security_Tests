package com.example.book_api;

import com.example.book_api.model.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApiApplication.class, args);
	}

	Book book1 = new Book("Book 1", "Author 1", "ISBN 1");
	Book book2 = new Book("Book 2", "Author 2", "ISBN 2");
	Book book3 = new Book("Book 3", "Author 3", "ISBN 3");
}
