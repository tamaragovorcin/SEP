package com.example.api.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
