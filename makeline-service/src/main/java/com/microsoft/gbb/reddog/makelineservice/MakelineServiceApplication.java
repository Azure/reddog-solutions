package com.microsoft.gbb.reddog.makelineservice;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCosmosRepositories
@EnableRedisRepositories
@EnableEurekaClient
public class MakelineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakelineServiceApplication.class, args);
    }

}
