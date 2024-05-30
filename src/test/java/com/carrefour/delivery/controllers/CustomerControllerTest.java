package com.carrefour.delivery.controllers;

import com.carrefour.delivery.entities.Customer;
import com.carrefour.delivery.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(CustomerController.class)
@ContextConfiguration
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setCustomer_id(1L);
        customer1.setName("customer 1");
        Customer customer2 = new Customer();
        customer2.setCustomer_id(2L);
        customer2.setName("customer 2");
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("customer 1"))
                .andExpect(jsonPath("$[1].name").value("customer 2"))
                .andDo(print());

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setCustomer_id(1L);
        customer.setName("customer 1");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));
        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("customer 1"))
                .andDo(print());
        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomer_id(1L);
        customer.setName("customer 1");
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"customer 1\", \"email\":\"customer1@delivery.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("customer 1"))
                .andDo(print());
        verify(customerService, times(1)).saveCustomer(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Long customerId = 1L;
        doNothing().when(customerService).deleteCustomer(customerId);
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}

