package com.bnp.bookstore.service;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.exception.BookNotFoundException;
import com.bnp.bookstore.exception.CustomBadRequestException;
import com.bnp.bookstore.mapper.OrderMapper;
import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartOrder;
import com.bnp.bookstore.model.OrderItem;
import com.bnp.bookstore.repository.CartRepository;
import com.bnp.bookstore.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookService bookService;

    public OrderDTO createOrder(String username) {
        Cart cart = cartRepository.findByAppUserUsername(username)
                .orElseThrow(() -> new CustomBadRequestException("Cart not found/Empty for user: " + username));

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    Book book = bookService.findByIsbn(cartItem.getBook().getIsbn())
                            .orElseThrow(() -> new BookNotFoundException("Book not found for ID: " + cartItem.getBook().getIsbn()));

                    return new OrderItem(book.getIsbn(), book.getPrice(), cartItem.getQuantity(), book.getTitle());
                })
                .collect(Collectors.toList());

        Double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        CartOrder cartOrder = new CartOrder(username, orderItems, totalAmount);
        CartOrder order = orderRepository.save(cartOrder);
        log.info("Order created for {}", order.getOrderNumber());
        log.info("Deleting the cart {}", cart.getId());
        cartRepository.delete(cart);
        return OrderMapper.toOrderDTO(order);
    }

    public List<OrderDTO> getOrdersForUser(String username) {
        List<CartOrder> orders = orderRepository.findByUsername(username);
        return OrderMapper.toOrderDTOList(orders);
    }

    public CartOrder getOrderById(Long orderId, String username) {
        return orderRepository.findById(orderId)
                .filter(cartOrder -> cartOrder.getUsername().equals(username))
                .orElseThrow(() -> new CustomBadRequestException("Order not found or does not belong to user: " + username));
    }

    public void cancelOrderForUser(String username, Long orderNumber) {
        Optional<CartOrder> order = orderRepository.findById(orderNumber);

        order.ifPresentOrElse(
                cartOrder -> orderRepository.delete(cartOrder),
                () -> {
                    throw new CustomBadRequestException("Order not found for order number : " + orderNumber);
                }
        );

    }
}