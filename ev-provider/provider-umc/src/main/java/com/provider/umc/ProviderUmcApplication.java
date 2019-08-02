package com.provider.umc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.provider.umc","com.ev.cloud.db"})
@MapperScan(basePackages = {"com.provider.umc.mapper","com.ev.cloud.db.mapper"})
@EnableDiscoveryClient
public class ProviderUmcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderUmcApplication.class, args);
	}

}
