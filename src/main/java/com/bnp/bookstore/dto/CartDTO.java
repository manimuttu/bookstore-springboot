package com.bnp.bookstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String username;
    private List<CartItemDTO> cartItems;

    public CartDTO(String username, List<CartItemDTO> cartItems) {
        this.username = username;
        this.cartItems = cartItems;
    }
}
