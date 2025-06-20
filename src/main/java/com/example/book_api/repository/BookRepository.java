package com.example.book_api.repository;

import com.example.book_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

Optional<Book> findByIsbn(String isbn);
}
