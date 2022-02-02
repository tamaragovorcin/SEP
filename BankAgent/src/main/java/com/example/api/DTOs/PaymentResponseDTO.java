package com.example.api.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
	private String paymentId;
	private String paymentUrl;
	
	public PaymentResponseDTO(Integer id, String url) {
		this.paymentId = id.toString();
		this.paymentUrl = url;
	}

	@Override
	public String toString() {
		return "PaymentResponseDTO{" +
				"paymentId='" + paymentId + '\'' +
				", paymentUrl='" + paymentUrl + '\'' +
				'}';
	}
}



