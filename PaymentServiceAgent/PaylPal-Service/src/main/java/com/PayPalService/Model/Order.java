package com.PayPalService.Model;

import lombok.*;

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
	private String clientId;
	private String clientSecret;

}
