package com.microsoft.gbb.reddog.makelineservice.messaging;

import com.microsoft.gbb.reddog.makelineservice.dto.OrderSummaryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TopicProducer {

    @Value("${spring.kafka.topic.completed-orders-name}")
    private String topicName;

    private final KafkaTemplate<String, OrderSummaryDto> kafkaTemplate;

    public TopicProducer(KafkaTemplate<String, OrderSummaryDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderSummaryDto message){
        log.info("Publishing:  {}", message);
        kafkaTemplate.send(topicName, message);
    }

}
