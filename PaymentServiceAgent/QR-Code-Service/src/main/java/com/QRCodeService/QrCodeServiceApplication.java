package com.QRCodeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class QrCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeServiceApplication.class, args);
	}

}
