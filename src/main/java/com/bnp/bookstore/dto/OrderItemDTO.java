package com.bnp.bookstore.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String bookIsbn;
    private String bookTitle;
    private Double price;
    private int quantity;
}
