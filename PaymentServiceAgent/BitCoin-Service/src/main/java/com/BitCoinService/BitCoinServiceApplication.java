package com.BitCoinService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BitCoinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitCoinServiceApplication.class, args);
	}

}
