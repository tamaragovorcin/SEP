package com.PayPalService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway", path= "bank-card-service/api/bankcard")
public interface BankCardFeignClient {

    @GetMapping("/getByName/{name}")
    public String getByName(@PathVariable String name);
}
