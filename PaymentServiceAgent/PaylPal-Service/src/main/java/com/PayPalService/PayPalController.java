package com.PayPalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PayPalController {

    @Autowired
    PayPalService payPalService;

    @GetMapping("")
    public String home() {
        return "paypal home";
    }

    @GetMapping("bankName")
    public String getBankName() {
        return payPalService.getBankCard();
    }
}
