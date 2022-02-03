package com.QRCodeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com/QRCodeService/FeignClients")
public class QrCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeServiceApplication.class, args);
	}

}
