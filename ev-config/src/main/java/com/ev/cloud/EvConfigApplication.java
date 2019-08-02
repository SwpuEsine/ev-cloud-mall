package com.ev.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
//@EnableDiscoveryClient	//注册为集群 方便横向扩展   配置中心
public class EvConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvConfigApplication.class, args);
	}

}
