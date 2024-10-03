package com.bnp.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public CartItem(Book book, int quantity, Cart cart) {
        this.book = book;
        this.quantity = quantity;
        this.cart = cart;
    }

    public CartItem() {
    }
}
