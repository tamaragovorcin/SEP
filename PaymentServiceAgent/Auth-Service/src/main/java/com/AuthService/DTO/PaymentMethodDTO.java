package com.AuthService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    private Integer webShopId;
    private List<String> methods;
    private String methodName;
    private List<String>disabledMethods;
}