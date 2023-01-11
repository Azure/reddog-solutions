package com.microsoft.gbb.reddog.makelineservice.config;

import com.microsoft.gbb.reddog.makelineservice.model.OrderSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl().and()
                .shutdownTimeout(Duration.ZERO)
                .build();
        RedisStandaloneConfiguration azureRedisConfig = new RedisStandaloneConfiguration(host, port);
        azureRedisConfig.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(azureRedisConfig, clientConfig);
    }

    @Bean(name = "reactiveRedisTemplate")
    ReactiveRedisTemplate<String, OrderSummary> reactiveRedisTemplate(
            @Qualifier("lettuceConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<OrderSummary> serializer = new Jackson2JsonRedisSerializer<>(OrderSummary.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, OrderSummary> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, OrderSummary> serializationContext = builder
                .value(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
