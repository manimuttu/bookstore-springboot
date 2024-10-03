package com.bnp.bookstore.service;

import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.dto.CartItemRequestDTO;
import com.bnp.bookstore.mapper.CartMapper;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartItem;
import com.bnp.bookstore.repository.BookRepository;
import com.bnp.bookstore.repository.CartItemRepository;
import com.bnp.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AppUserService appUserService;

    public CartDTO addOrUpdateBooksCart(List<CartItemRequestDTO> requestDTOs, String username) {
        AppUser appUser = appUserService.findByUsername(username).orElseThrow();

        Cart cart = cartRepository.findByAppUser(appUser).map(existingCart -> {
            existingCart.getCartItems().clear();
            return existingCart;
        }).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setAppUser(appUser);
            return cartRepository.save(newCart);
        });

        for (CartItemRequestDTO itemDto : requestDTOs) {
            Book book = bookService.findByIsbn(itemDto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found for ISBN: " + itemDto.getBookId()));

            if (book.getStock() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Book with ISBN " + itemDto.getBookId() +
                        " has insufficient stock. Stock available: " + book.getStock());
            }

            CartItem cartItem = toCartItem(book, itemDto.getQuantity(), cart);
            cart.addCartItem(cartItem);
        }

        cartRepository.save(cart);
        return CartMapper.toCartDTO(cart);
    }

    private CartItem toCartItem(Book book, int quantity, Cart cart) {
        return new CartItem(book, quantity, cart);
    }

    public CartDTO getCartForUser(String username) {
        AppUser user = appUserService.findByUsername(username).orElseThrow();
        Cart savedCart = cartRepository.findByAppUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setAppUser(user);
            return cartRepository.save(cart);
        });

        return CartMapper.toCartDTO(savedCart);
    }

}
