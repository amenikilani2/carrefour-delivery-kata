package com.carrefour.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-events", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }
}
