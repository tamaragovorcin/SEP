package com.BitCoinService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com/BitCoinService/FeignClients")
public class BitCoinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitCoinServiceApplication.class, args);
	}

}
