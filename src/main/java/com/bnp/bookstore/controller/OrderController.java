package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookstore/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam String username) {
        OrderDTO createdCartOrder = orderService.createOrder(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCartOrder);
    }

    @GetMapping("/userOrders")
    public ResponseEntity<List<OrderDTO>> getOrdersForUser(@RequestParam String username) {
        List<OrderDTO> cartOrders = orderService.getOrdersForUser(username);
        return ResponseEntity.ok(cartOrders);
    }

    @DeleteMapping("/cancel/{order_number}")
    public ResponseEntity<String> cancelOrderForUser(@RequestParam String username,
                                                     @PathVariable("order_number") Long orderNumber) {
        orderService.cancelOrderForUser(username, orderNumber);
        return ResponseEntity.ok("Order Cancelled");
    }
}
