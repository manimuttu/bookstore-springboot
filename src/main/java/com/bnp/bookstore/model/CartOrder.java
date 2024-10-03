package com.bnp.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class CartOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    private Double totalAmount;

    public CartOrder(String username, List<OrderItem> orderItems, Double totalAmount) {
        this.username = username;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
    }

    public CartOrder() {
    }
}