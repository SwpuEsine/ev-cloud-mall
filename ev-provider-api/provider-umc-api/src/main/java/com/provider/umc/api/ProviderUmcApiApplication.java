package com.provider.umc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
public class ProviderUmcApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderUmcApiApplication.class, args);
	}

}
