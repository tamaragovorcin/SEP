package com.BankCardService.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoDTO {
    private String cardHolderName;
    private String cardSecurityCode;
    private String pan;
    private YearMonth expirationDate;

}
