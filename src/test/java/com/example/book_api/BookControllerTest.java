// src/test/java/com/example/bookapi/controller/BookControllerTest.java
package com.example.book_api;

import com.example.book_api.model.Book;
import com.example.book_api.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = {"USER"})
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BookRepository bookRepository;

	@BeforeEach
	void setup() {
		bookRepository.deleteAll(); // Nettoyer la base de données avant chaque test
	}

	@Test
	void createBook_shouldReturnCreatedBook() throws Exception {
		Book book = new Book(null, "New Title", "New Author", "1111111111");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.title", is("New Title")));
	}

	@Test
	void getAllBooks_shouldReturnListOfBooks() throws Exception {

		bookRepository.save(new Book(null, "Book 1", "Author 1", "ISBN-001"));
		bookRepository.save(new Book(null, "Book 2", "Author 2", "ISBN-002"));


		mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", is("Book 1")))
				.andExpect(jsonPath("$[1].title", is("Book 2")));
	}

	@Test
	void getBookById_shouldReturnBook() throws Exception {

		Book book = bookRepository.save(new Book(null, "Find Me", "Me", "ABC-123"));


		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", book.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Find Me")));
	}

	@Test
	void getBookById_shouldReturnNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 999L))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateBook_shouldReturnUpdatedBook() throws Exception {

		Book existingBook = bookRepository.save(new Book(null, "Old Title", "Old Author", "OLD-ISBN"));
		Book updatedDetails = new Book(null, "Updated Title", "Updated Author", "UPDATED-ISBN");


		mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", existingBook.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedDetails)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Updated Title")))
				.andExpect(jsonPath("$.isbn", is("UPDATED-ISBN")));
	}

	@Test
	void deleteBook_shouldReturnNoContent() throws Exception {

		Book bookToDelete = bookRepository.save(new Book(null, "To Delete", "Author", "DEL-ISBN"));


		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookToDelete.getId()))
				.andExpect(status().isNoContent()); // Vérifie 204 No Content


		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookToDelete.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	void getBookByIsbn_shouldReturnBook() throws Exception {

		Book book = bookRepository.save(new Book(null, "ISBN Book", "ISBN Author", "UNIQUE-ISBN-123"));


		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/isbn/{isbn}", book.getIsbn()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("ISBN Book")));
	}

}