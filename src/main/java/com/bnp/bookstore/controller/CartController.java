package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.dto.CartItemRequestDTO;
import com.bnp.bookstore.repository.CartRepository;
import com.bnp.bookstore.service.AppUserService;
import com.bnp.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookstore/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/items")
    public ResponseEntity<CartDTO> getCartDetails(@RequestParam String username){
        CartDTO cart = cartService.getCartForUser(username);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addOrUpdateBooksCart(@RequestBody List<CartItemRequestDTO> items, @RequestParam String username){
        CartDTO cart = cartService.addOrUpdateBooksCart(items,username);
        return ResponseEntity.ok(cart);
    }
}
