package com.ev.cloud.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ev.cloud","com.ev.common.core"})
public class EvDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvDbApplication.class, args);
	}

}
