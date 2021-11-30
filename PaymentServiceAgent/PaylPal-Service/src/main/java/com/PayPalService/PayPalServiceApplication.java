package com.PayPalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com/PayPalService/FeignClients")
public class PayPalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayPalServiceApplication.class, args);
	}

}
