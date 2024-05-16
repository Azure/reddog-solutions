package com.microsoft.gbb.reddog.localeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LocalEurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LocalEurekaServerApplication.class, args);
	}

}
