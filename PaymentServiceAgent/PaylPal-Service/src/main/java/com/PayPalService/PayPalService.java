package com.PayPalService;

import com.PayPalService.FeignClients.BankCardFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    @Autowired
    BankCardFeignClient bankCardFeignClient;

    public String getBankCard() {
        return bankCardFeignClient.getByName(" path variable");
    }
}
