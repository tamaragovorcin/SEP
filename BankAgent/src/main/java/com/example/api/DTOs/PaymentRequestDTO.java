package com.example.api.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDTO {

	private String merchantId;
	private String merchantPassword;
	private Double amount;
	private Integer merchantOrderId;
	private Date merchantTimestamp;
	private String successUrl;
	private String failedUrl;
	private String errorUrl;	
	private String callbackUrl;

	@Override
	public String toString() {
		return "PaymentRequestDTO{" +
				"merchantId='" + merchantId + '\'' +
				", merchantPassword='" + merchantPassword + '\'' +
				", amount=" + amount +
				", merchantOrderId=" + merchantOrderId +
				", merchantTimestamp=" + merchantTimestamp +
				", successUrl='" + successUrl + '\'' +
				", failedUrl='" + failedUrl + '\'' +
				", errorUrl='" + errorUrl + '\'' +
				", callbackUrl='" + callbackUrl + '\'' +
				'}';
	}
}
