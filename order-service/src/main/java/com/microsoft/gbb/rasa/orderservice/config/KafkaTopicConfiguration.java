package com.microsoft.gbb.rasa.orderservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    public static final int PARTITION_COUNT = 3;
    public static final int REPLICA_COUNT = 3;
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.security.protocol}")
    private String securityProtocol;
    @Value("${spring.kafka.producer.properties.sasl.mechanism}")
    private String saslMechanism;
    @Value("${spring.kafka.producer.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put("sasl.mechanism", saslMechanism);
        configs.put("sasl.jaas.config", saslJaasConfig);
        configs.put("security.protocol", securityProtocol);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name(topicName)
                .partitions(PARTITION_COUNT)
                .replicas(REPLICA_COUNT)
                .build();
    }
}
