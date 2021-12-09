package com.BitCoinService;

import com.BitCoinService.Model.BitcoinPaymentDTO;
import com.BitCoinService.Model.MerchantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bitcoin")
public class BitCoinController {

    @Autowired
    BitcoinService bitcoinService;

    @GetMapping("")
    public String home() {
        return "bitcoin home";
    }


    @PostMapping(value = "/pay")
    public String startPayment(@RequestBody MerchantDTO merchantDTO){

        return bitcoinService.createPayment(merchantDTO);
    }

    @PostMapping(value = "/save")
    public void startPayment(@RequestBody BitcoinPaymentDTO bitcoinPaymentDTO){

         bitcoinService.savePayment(bitcoinPaymentDTO);
    }

}
