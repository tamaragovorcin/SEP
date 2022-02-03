package com.example.api.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AcquirerResponseDTO {
	public AcquirerResponseDTO(PccResponseDTO body) {
		this.isAuthentificated = body.getIsAuthentificated();
		this.isTransactionAutorized = body.getIsTransactionAutorized();
	}
	private Boolean isAuthentificated;
	private Boolean isTransactionAutorized;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
	private Integer issuerOrderId;
	private Date issuerTimestamp;
}
