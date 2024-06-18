package com.microsoft.gbb.reddog.virtualworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VirtualWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualWorkerApplication.class, args);
    }

}
