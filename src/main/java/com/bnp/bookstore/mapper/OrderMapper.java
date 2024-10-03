package com.bnp.bookstore.mapper;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.dto.OrderItemDTO;
import com.bnp.bookstore.model.CartOrder;
import com.bnp.bookstore.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toOrderDTO(CartOrder order) {
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setUsername(order.getUsername());
        orderDTO.setOrderItems(toOrderItemDTOList(order.getOrderItems()));

        return orderDTO;
    }

    public static List<OrderDTO> toOrderDTOList(List<CartOrder> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    public static List<OrderItemDTO> toOrderItemDTOList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList());
    }

    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setBookIsbn(orderItem.getBookIsbn());
        orderItemDTO.setBookTitle(orderItem.getBookTitle());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setQuantity(orderItem.getQuantity());

        return orderItemDTO;
    }
}
