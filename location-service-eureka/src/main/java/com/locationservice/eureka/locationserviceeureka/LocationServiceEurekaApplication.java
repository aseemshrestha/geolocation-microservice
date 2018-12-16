package com.locationservice.eureka.locationserviceeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LocationServiceEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationServiceEurekaApplication.class, args);
	}

}

