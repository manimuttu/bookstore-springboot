package com.bnp.bookstore.mapper;

import com.bnp.bookstore.dto.CartDTO;
import com.bnp.bookstore.dto.CartItemDTO;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(CartMapper::toCartItemDTO)
                .collect(Collectors.toList());
        
        return new CartDTO(cart.getAppUser().getUsername(), cartItemDTOs);
    }

    public static CartItemDTO toCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getBook().getIsbn(),
                cartItem.getBook().getTitle(),
                cartItem.getBook().getPrice(),
                cartItem.getQuantity()
        );
    }
}
