package com.bnp.bookstore.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private String bookId;
    private String bookTitle;
    private Double price;
    private int quantity;

    public CartItemDTO(String bookId, String bookTitle, Double price, int quantity) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quantity;
    }
}
