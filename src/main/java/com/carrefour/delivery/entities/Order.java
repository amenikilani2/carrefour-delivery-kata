package com.carrefour.delivery.entities;

import com.carrefour.delivery.utils.DeliveryMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private LocalDateTime deliveryDate;

    private DeliveryMethod deliveryMethod;

    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name= "customer_id")
    private Customer customer;
}
