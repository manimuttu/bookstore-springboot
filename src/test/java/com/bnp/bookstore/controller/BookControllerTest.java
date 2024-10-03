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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void getAllBooks_shouldReturnListOfBooks_whenBooksExist() throws Exception {
        // Arrange
        Book book1 = new Book();
        book1.setAuthor("Author1");
        book1.setIsbn("AS1234");
        book1.setPrice(200.00);
        book1.setStock(20);
        book1.setTitle("Book1 Title");

        Book book2 = new Book();
        book2.setAuthor("Author2");
        book2.setIsbn("BS1234");
        book2.setPrice(500.00);
        book2.setStock(40);
        book2.setTitle("Book2 Title");

        List<Book> books = List.of(book1, book2);
        when(bookService.listAllBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/bookstore/books/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book1 Title"))
                .andExpect(jsonPath("$[1].title").value("Book2 Title"));
    }
}
