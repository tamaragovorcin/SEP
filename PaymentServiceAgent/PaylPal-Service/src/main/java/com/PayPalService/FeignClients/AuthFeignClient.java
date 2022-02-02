package com.PayPalService.FeignClients;

import com.PayPalService.DTO.UrlAddressesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway", path= "auth-service/api/webshop")
public interface AuthFeignClient {

    @GetMapping("/urls/{webShopId}")
    public UrlAddressesDTO getUrlsByWebShopId(@PathVariable String webShopId);
}
