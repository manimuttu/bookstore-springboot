package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.OrderDTO;
import com.bnp.bookstore.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createOrder_ValidOrder_ReturnsOrderDTO() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(1L);
        orderDTO.setTotalAmount(100.0);
        orderDTO.setUsername("testuser");

        when(orderService.createOrder("testuser")).thenReturn(orderDTO);

        mockMvc.perform(post("/bookstore/orders/checkout?username=testuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(100.0))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void listOrders_UserHasOrders_ReturnsOrderList() throws Exception {
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setOrderNumber(1L);
        orderDTO1.setTotalAmount(100.0);
        orderDTO1.setUsername("testuser");

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setOrderNumber(2L);
        orderDTO2.setTotalAmount(150.0);
        orderDTO2.setUsername("testuser");

        when(orderService.getOrdersForUser("testuser")).thenReturn(List.of(orderDTO1, orderDTO2));

        mockMvc.perform(get("/bookstore/orders/userOrders?username=testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderNumber").value(1L))
                .andExpect(jsonPath("$[1].orderNumber").value(2L));
    }
}
