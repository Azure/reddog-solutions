package com.microsoft.gbb.rasa.orderservice.messaging;

import com.microsoft.gbb.rasa.orderservice.entities.OrderSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TopicProducer {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, OrderSummary> kafkaTemplate;

    public TopicProducer(KafkaTemplate<String, OrderSummary> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderSummary message){
        log.info("Payload: {}", message);
        kafkaTemplate.send(topicName, message);
    }

}
