package com.example.api.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PccRequestDTO {
	private String panNumber;
	private String cardHolder;
	private Integer cvv;
	private String expirationDate;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
	private Double amount;
}
