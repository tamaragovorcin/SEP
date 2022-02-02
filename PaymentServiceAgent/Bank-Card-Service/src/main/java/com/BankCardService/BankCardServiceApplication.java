package com.BankCardService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
public class BankCardServiceApplication {


	public static void main(String[] args) {

		SpringApplication.run(BankCardServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
