// src/main/java/com/example/bookapi/service/BookService.java
package com.example.book_api.service;

import com.example.book_api.model.Book;
import com.example.book_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById((id));
    }


    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }


    @PreAuthorize("hasRole('ADMIN') or @bookRepository.findById(#id).get().getAuthor() == authentication.name")
    public Book updateBook(Long id, Book bookDetails) {
        Book existingBook = bookRepository.findById((id))
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setIsbn(bookDetails.getIsbn());


        return bookRepository.save(existingBook);
    }


    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById((id));
            return true;
        }
        return false;
    }
}