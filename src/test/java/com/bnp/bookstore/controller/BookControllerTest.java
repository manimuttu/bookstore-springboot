package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.repository.BookRepository;
import com.bnp.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testAddBooks() throws Exception {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        List<Book> books = List.of(book1, book2);

        when(bookService.addBooks(books)).thenReturn(books);

        mockMvc.perform(post("/bookstore/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddBooksService() throws Exception {
        Book book1 = new Book();
        book1.setAuthor("Author");
        book1.setIsbn("AS1234");
        book1.setPrice(200.00);
        book1.setStock(20);
        book1.setTitle("Title");

        List<Book> books = List.of(book1);

        when(bookRepository.saveAll(books)).thenReturn(books);

        mockMvc.perform(post("/bookstore/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isCreated());
    }
}
