package com.PayPalService;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {

	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

}
