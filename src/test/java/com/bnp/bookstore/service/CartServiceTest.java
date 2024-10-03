package com.bnp.bookstore.service;

import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.dto.CartItemRequestDTO;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartItem;
import com.bnp.bookstore.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private CartService cartService;


    @Test
    public void addBooksToCart_ValidItems_AddsToCartSuccessfully() {
        String username = "testuser";
        AppUser user = new AppUser();
        user.setUsername(username);

        Book book1 = new Book();
        book1.setIsbn("DFE45323");
        book1.setTitle("Book 1");
        book1.setPrice(10.0);
        book1.setStock(10);

        Book book2 = new Book();
        book2.setIsbn("JHGE234");
        book2.setTitle("Book 2");
        book2.setPrice(15.0);
        book2.setStock(20);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setAppUser(user);

        List<CartItemRequestDTO> itemRequests = List.of(
                new CartItemRequestDTO("DFE45323", 2),
                new CartItemRequestDTO("JHGE234", 1)
        );

        when(appUserService.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByAppUser(user)).thenReturn(Optional.of(cart));
        when(bookService.findByIsbn("DFE45323")).thenReturn(Optional.of(book1));
        when(bookService.findByIsbn("JHGE234")).thenReturn(Optional.of(book2));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDTO result = cartService.addOrUpdateBooksCart(itemRequests, username);

        assertEquals(2, result.getCartItems().size());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void listCart_UserHasCart_ReturnsCartItems() {
        String username = "testuser";
        AppUser user = new AppUser();
        user.setUsername(username);

        Book book1 = new Book();
        book1.setIsbn("DFE45323");
        book1.setTitle("Book 1");
        book1.setPrice(10.0);

        CartItem cartItem1 = new CartItem();
        cartItem1.setBook(book1);
        cartItem1.setQuantity(2);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setAppUser(user);
        cart.setCartItems(List.of(cartItem1));

        when(appUserService.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByAppUser(user)).thenReturn(Optional.of(cart));

        CartDTO result = cartService.getCartForUser(username);

        assertEquals(1, result.getCartItems().size());
        assertEquals("Book 1", result.getCartItems().get(0).getBookTitle());
        verify(cartRepository, times(1)).findByAppUser(user);
    }
}
