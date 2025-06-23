// src/main/java/com/example/bookapi/controller/BookController.java
package com.example.book_api.controller;

import com.example.book_api.model.Book;
import com.example.book_api.service.BookService; // Importer le service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService; // Utiliser le service

    @Autowired
    public BookController(BookService bookService) { // Injecter le service
        this.bookService = bookService;
    }

    // Endpoint pour créer un nouveau livre
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book); // Appeler le service
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer tous les livres
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks(); // Appeler le service
    }

    // Endpoint pour récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id); // Appeler le service
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour mettre à jour un livre
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails); // Appeler le service
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) { // Attraper l'exception si le livre n'est pas trouvé
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id); // Appeler le service
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> book = bookService.getBookByIsbn(isbn); // Appeler le service
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}