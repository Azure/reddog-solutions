package com.microsoft.gbb.rasa.makelineservice;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCosmosRepositories
@EnableRedisRepositories
public class MakelineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakelineServiceApplication.class, args);
    }

}
