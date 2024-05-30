package com.carrefour.delivery.controllers;

import com.carrefour.delivery.entities.Customer;
import com.carrefour.delivery.entities.Order;
import com.carrefour.delivery.services.CustomerService;
import com.carrefour.delivery.services.OrderService;
import com.carrefour.delivery.utils.DeliveryMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ContextConfiguration
class OrderControllerTest {

        @MockBean
        private OrderService orderService;

        @InjectMocks
        private OrderController orderController;

        @Autowired
        private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        }

        @Test
        void testGetAllOrders() throws Exception {
            Order order1 = new Order();
            order1.setOrder_id(1L);
            Order order2 = new Order();
            order2.setOrder_id(2L);
            when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));
            mockMvc.perform(get("/api/orders"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andDo(print());
            verify(orderService, times(1)).getAllOrders();
        }

        @Test
        void testGetOrderById() throws Exception {
            Order order = new Order();
            order.setOrder_id(1L);
            when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
            mockMvc.perform(get("/api/orders/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.order_id").value(1))
                    .andDo(print());
            verify(orderService, times(1)).getOrderById(1L);
        }

        @Test
        void testCreateOrder() throws Exception {
            Order order = new Order();
            order.setOrder_id(1L);
            Customer customer = new Customer();
            customer.setCustomer_id(1L);
            customer.setName("customer 1");
            order.setCustomer(customer);
            order.setDeliveryAddress("2 avenue test");
            order.setDeliveryMethod(DeliveryMethod.DRIVE);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(order);
            when(orderService.saveOrder(any(Order.class))).thenReturn(order);
            mockMvc.perform(post("/api/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.order_id").value(1))
                    .andDo(print());
            verify(orderService, times(1)).saveOrder(any(Order.class));
        }

        @Test
        void testDeleteOrder() throws Exception {
            Long orderId = 1L;
            doNothing().when(orderService).deleteOrder(orderId);
            mockMvc.perform(delete("/api/orders/1"))
                    .andExpect(status().isNoContent())
                    .andDo(print());
            verify(orderService, times(1)).deleteOrder(orderId);
        }

}

