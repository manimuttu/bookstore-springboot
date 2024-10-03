package com.bnp.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookIsbn;

    private String bookTitle;
    private Double price;
    private int quantity;

    public OrderItem(String bookIsbn, Double price, int quantity, String bookTitle) {
        this.bookIsbn = bookIsbn;
        this.price = price;
        this.quantity = quantity;
        this.bookTitle = bookTitle;
    }

    public OrderItem() {
    }
}
