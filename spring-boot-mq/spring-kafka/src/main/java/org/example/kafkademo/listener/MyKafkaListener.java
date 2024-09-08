package org.example.kafkademo.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

    @KafkaListener(topics = "topic_demo_001",groupId = "custom-1",concurrency = "3")
    void pullMessage(String message) {
        System.out.println(message);
    }
}
