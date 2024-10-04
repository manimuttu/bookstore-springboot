package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.repository.BookRepository;
import com.bnp.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldAddAllBooks_whenAddBooksIsCalled() throws Exception {
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
    public void shouldAddBook_whenAddBooksIsCalled() throws Exception {
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

        mockMvc.perform(get("/bookstore/books/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book1 Title"))
                .andExpect(jsonPath("$[1].title").value("Book2 Title"));
    }
}
