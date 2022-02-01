package com.example.api.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PccResponseDTO {
	private Boolean isAuthentificated;
	private Boolean isTransactionAutorized;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
}
