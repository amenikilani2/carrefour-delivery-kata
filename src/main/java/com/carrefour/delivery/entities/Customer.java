package com.carrefour.delivery.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="customers")
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    private String name;
    private String phoneNumber;
    private String email;

    @OneToMany(targetEntity=Order.class, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
