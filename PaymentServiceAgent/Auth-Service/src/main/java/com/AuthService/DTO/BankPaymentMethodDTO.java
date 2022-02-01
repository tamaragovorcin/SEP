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
public class BankPaymentMethodDTO {
    private Integer webShopId;
    private List<String> methods;
    private String methodName;
    private List<String>disabledMethods;
    private String merchantID;
    private String merchantPassword;
    private Integer bank;
}
