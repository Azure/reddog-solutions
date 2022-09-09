package com.microsoft.gbb.reddog.makelineservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KafkaPublisher {

    @Value("${messaging.pubsub.TOPIC_NAME}")
    private String TOPIC_NAME;
    @Value("${messaging.pubsub.SUB_NAME}")
    private String SUB_NAME;

    public void publishEvent(String message) {
        // TODO: Publish message to Event Hubs Kafka endpoint
    }

}

