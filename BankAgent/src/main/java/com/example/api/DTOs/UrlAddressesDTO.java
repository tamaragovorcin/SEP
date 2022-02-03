package com.example.api.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlAddressesDTO {
    private String successUrl;
    private String errorUrl;
    private String failureUrl;
}
