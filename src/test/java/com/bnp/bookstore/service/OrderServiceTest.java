package com.bnp.bookstore.service;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartOrder;
import com.bnp.bookstore.repository.CartRepository;
import com.bnp.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createOrder_ValidOrder_ReturnsOrderDTO() {
        // Given
        CartOrder order = new CartOrder();
        order.setOrderNumber(1L);
        order.setTotalAmount(100.0);
        order.setUsername("testuser");
        order.setOrderItems(new ArrayList<>());

        String username = "testuser";
        AppUser user = new AppUser();
        user.setUsername(username);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setAppUser(user);

        when(orderRepository.save(any(CartOrder.class))).thenReturn(order);
        when(cartRepository.findByAppUserUsername("testuser")).thenReturn(Optional.of(cart));

        OrderDTO result = orderService.createOrder("testuser");

        assertEquals(1L, result.getOrderNumber());
        assertEquals(100.0, result.getTotalAmount());
        assertEquals("testuser", result.getUsername());
        verify(orderRepository, times(1)).save(any(CartOrder.class));
    }

    @Test
    public void listOrders_UserHasOrders_ReturnsOrderList() {
        CartOrder order1 = new CartOrder();
        order1.setOrderNumber(1L);
        order1.setTotalAmount(100.0);
        order1.setUsername("testuser");
        order1.setOrderItems(new ArrayList<>());

        CartOrder order2 = new CartOrder();
        order2.setOrderNumber(2L);
        order2.setTotalAmount(150.0);
        order2.setUsername("testuser");
        order2.setOrderItems(new ArrayList<>());

        List<CartOrder> orders = List.of(order1, order2);
        when(orderRepository.findByUsername("testuser")).thenReturn(orders);

        List<OrderDTO> result = orderService.getOrdersForUser("testuser");

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getOrderNumber());
        assertEquals(2L, result.get(1).getOrderNumber());
        verify(orderRepository, times(1)).findByUsername("testuser");
    }
}
