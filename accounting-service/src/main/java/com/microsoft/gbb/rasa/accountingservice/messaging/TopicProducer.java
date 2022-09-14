package com.microsoft.gbb.rasa.accountingservice.messaging;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TopicProducer {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, OrderSummaryDto> kafkaTemplate;

    public TopicProducer(KafkaTemplate<String, OrderSummaryDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderSummaryDto message){
        log.info("Payload: {}", message);
        kafkaTemplate.send(topicName, message);
    }

}
