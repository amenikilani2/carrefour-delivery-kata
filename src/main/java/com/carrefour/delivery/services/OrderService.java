package com.carrefour.delivery.services;

import com.carrefour.delivery.entities.Order;
import com.carrefour.delivery.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Cacheable(value = "orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Cacheable(value = "order", key = "#id")
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    @CachePut(value = "order", key = "#order.id")
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    @CacheEvict(value = "order", key = "#id")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
