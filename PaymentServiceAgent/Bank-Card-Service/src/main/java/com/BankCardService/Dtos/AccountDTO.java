package com.BankCardService.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String cardHolderName;
    private String cardHolderUCIN;
    private Integer bankId;
    private Double availableFunds;
    private Double reservedFunds;
    private String PAN;
    private String mm;
    private String yy;
    private String cardSecurityCode;
}
