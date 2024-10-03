package com.bnp.bookstore.mapper;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.model.CartOrder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderMapperTest {

    @Test
    public void testToOrderDTO() {
        CartOrder order = new CartOrder();
        order.setOrderNumber(1L);
        order.setTotalAmount(100.0);
        order.setUsername("testuser");
        order.setOrderItems(new ArrayList<>());

        OrderDTO orderDTO = OrderMapper.toOrderDTO(order);

        assertEquals(order.getTotalAmount(), orderDTO.getTotalAmount());
        assertEquals(order.getUsername(), orderDTO.getUsername());
    }

    @Test
    public void testToOrderDTOList() {
        CartOrder order1 = new CartOrder();
        order1.setOrderNumber(1L);
        order1.setTotalAmount(100.0);
        order1.setUsername("testuser1");
        order1.setOrderItems(new ArrayList<>());

        CartOrder order2 = new CartOrder();
        order2.setOrderNumber(2L);
        order2.setTotalAmount(150.0);
        order2.setUsername("testuser2");
        order2.setOrderItems(new ArrayList<>());

        List<CartOrder> orders = Arrays.asList(order1, order2);

        List<OrderDTO> orderDTOs = OrderMapper.toOrderDTOList(orders);

        assertEquals(2, orderDTOs.size());
        assertEquals(order1.getTotalAmount(), orderDTOs.get(0).getTotalAmount());
        assertEquals(order2.getUsername(), orderDTOs.get(1).getUsername());
    }
}
