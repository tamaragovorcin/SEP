package com.BitCoinService.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDTO {
	
	private BigDecimal amount;
	private String merchant_id;
	private String merchant_token;
	
}
