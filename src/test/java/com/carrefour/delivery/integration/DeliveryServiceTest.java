package com.carrefour.delivery.integration;

import com.carrefour.delivery.config.TestConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = { "order-events" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class DeliveryServiceTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Consumer<String, String> consumer;

    @BeforeEach
    public void setUp() {
        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "true", kafkaContainer.getBootstrapServers()));
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of("order-events"));
    }

    @Test
    public void testCreateOrderAndConsumeEvent() {
        // Create a new order
        String orderJson = "{\"customerId\":\"123\", \"deliveryMethod\": \"Drive\", \"deliveryDateTime\": \"2024-05-29T09:00:00\", \"deliveryAddress\": \"2 avenue test\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(orderJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/orders", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "order-events");
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.value()).contains("Test order");
    }
}
