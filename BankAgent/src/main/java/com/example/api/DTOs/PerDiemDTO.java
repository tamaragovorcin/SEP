package com.example.api.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerDiemDTO {
    private String cardHolderName;
    private String giroNumber;
    private String referenceNumber;
    private Double amount;
}
