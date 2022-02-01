package com.BankCardService.Model;

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
public class Transaction {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Double amount;
		
	@Column
	private String merchantId;

	@Column
	private Integer merchantOrderId;
	
	@Column
	private Date merchantTimestamp;
	
	@Column
	private String panNumber;
	
	@Column
	private TransactionStatus status;

}
