package com.example.api.entities.bank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PaymentRequest {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String merchantId;
	
	@Column(nullable = false)
	private String merchantPassword;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private Integer merchantOrderId;
	
	@Column(nullable = false)
	private Date merchantTimestamp;
	
	@Column
	private String callbackUrl;
	
	@Column(nullable = false)
	private String successUrl;
	
	@Column(nullable = false)
	private String failedUrl;
	
	@Column(nullable = false)
	private String errorUrl;


	@Column(nullable = false)
	private String paymentId;


	@Column(nullable = false)
	private String paymentUrl;

	@Override
	public String toString() {
		return "PaymentRequest{" +
				"id=" + id +
				", merchantId='" + merchantId + '\'' +
				", merchantPassword='" + merchantPassword + '\'' +
				", amount=" + amount +
				", merchantOrderId=" + merchantOrderId +
				", merchantTimestamp=" + merchantTimestamp +
				", callbackUrl='" + callbackUrl + '\'' +
				", successUrl='" + successUrl + '\'' +
				", failedUrl='" + failedUrl + '\'' +
				", errorUrl='" + errorUrl + '\'' +
				", paymentId='" + paymentId + '\'' +
				", paymentUrl='" + paymentUrl + '\'' +
				'}';
	}
}
