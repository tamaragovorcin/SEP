package com.example.api.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

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
}
