package com.AuthService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebShopDTO {

    private String webShopId;
    private String username;
    private String password;
    private String successUrl;
    private String errorUrl;
    private String failureUrl;
    private String currency;
}
