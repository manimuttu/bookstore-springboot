package com.bnp.bookstore.service;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldAddAllBooks_whenAddBooksIsCalled() {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.saveAll(books)).thenReturn(books);
        List<Book> addedBooks = bookService.addBooks(books);

        assertEquals(2, addedBooks.size());
        verify(bookRepository, times(1)).saveAll(books);
    }

    @Test
    public void shouldReturnAllBooks_whenGetAllBooksIsCalled() {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> listedBooks = bookService.listAllBooks();

        assertEquals(2, listedBooks.size());
        verify(bookRepository, times(1)).findAll();
    }
}
