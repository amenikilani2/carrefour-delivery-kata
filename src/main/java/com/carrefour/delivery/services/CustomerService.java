package com.carrefour.delivery.services;

import com.carrefour.delivery.entities.Customer;
import com.carrefour.delivery.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Cacheable(value = "customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Cacheable(value = "customer", key = "#id")
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @CachePut(value = "customer", key = "#customer.id")
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = "customer", key = "#id")
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
