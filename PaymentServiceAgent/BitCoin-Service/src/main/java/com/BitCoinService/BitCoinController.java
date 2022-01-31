package com.BitCoinService;

import com.BitCoinService.Model.BitcoinPaymentDTO;
import com.BitCoinService.Model.MerchantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/bitcoin")
public class BitCoinController {

    @Autowired
    BitcoinService bitcoinService;

    Logger logger = LoggerFactory.getLogger(BitCoinController.class);

    @GetMapping("")
    public String home() {
        return "bitcoin home";
    }

    @PostMapping(value = "/pay")
    public ResponseEntity<String> startPayment(@RequestBody MerchantDTO merchantDTO){
        String response = "";
        try {
            response = bitcoinService.createPayment(merchantDTO);
            logger.info("Bitcoin payment object created.");
        }
        catch (Exception e){
            logger.error("Exception with creating bitcoin payment object. Error is: " + e );
        }
        return response == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping(value = "/save")
    public void startPayment(@RequestBody BitcoinPaymentDTO bitcoinPaymentDTO){
        System.out.println("78779878798");
        try {
            bitcoinService.savePayment(bitcoinPaymentDTO);
            logger.info("Bitcoin payment successfully saved.");

        }
        catch (Exception e){
            logger.error("Exception with saving bitcoin payment object. Error is: " + e );
        }

    }
}
