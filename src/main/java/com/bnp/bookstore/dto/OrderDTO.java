package com.bnp.bookstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long orderNumber;
    private Double totalAmount;
    private String username;
    private List<OrderItemDTO> orderItems;
}
