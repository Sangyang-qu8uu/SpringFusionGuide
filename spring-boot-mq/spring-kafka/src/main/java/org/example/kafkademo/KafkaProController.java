package org.example.kafkademo;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/kafka/push")
public class KafkaProController {

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("/push")
    public void push() {
        String message = "hello world";
        kafkaTemplate.send("topic_demo_001", JSON.toJSON(message));
    }
}