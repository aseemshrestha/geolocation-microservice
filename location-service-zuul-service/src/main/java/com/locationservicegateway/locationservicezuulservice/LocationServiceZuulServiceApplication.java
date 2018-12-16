package com.locationservicegateway.locationservicezuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class LocationServiceZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationServiceZuulServiceApplication.class, args);
	}

}

