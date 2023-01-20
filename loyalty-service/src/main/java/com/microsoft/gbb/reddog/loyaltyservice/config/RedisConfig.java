package com.microsoft.gbb.reddog.loyaltyservice.config;

import com.microsoft.gbb.reddog.loyaltyservice.model.LoyaltySummary;
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
    ReactiveRedisTemplate<String, LoyaltySummary> reactiveRedisTemplate(
            @Qualifier("lettuceConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<LoyaltySummary> serializer = new Jackson2JsonRedisSerializer<>(LoyaltySummary.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, LoyaltySummary> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, LoyaltySummary> serializationContext = builder
                .value(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
