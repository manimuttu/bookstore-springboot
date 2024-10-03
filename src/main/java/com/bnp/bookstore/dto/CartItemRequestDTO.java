package com.bnp.bookstore.dto;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private String bookId;
    private int quantity;

    public CartItemRequestDTO(String bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}