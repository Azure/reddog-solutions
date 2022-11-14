package com.microsoft.gbb.reddog.localconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigServer
public class LocalConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalConfigServiceApplication.class, args);
	}

}
