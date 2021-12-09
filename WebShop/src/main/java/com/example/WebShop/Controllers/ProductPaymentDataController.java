package com.example.WebShop.Controllers;

import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Service.IServices.IProductPaymentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/product/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductPaymentDataController {

    @Autowired
    IProductPaymentDataService productPaymentDataService;

    @GetMapping("/paypal")
    public ResponseEntity<PaypalCredentialsDTO> findPaypalCredentials() {
        PaypalCredentialsDTO paypalCredentialsDTO = productPaymentDataService.findPaypalCredentials();

        return paypalCredentialsDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paypalCredentialsDTO);
    }

    @GetMapping("/bitcoin")
    public ResponseEntity<String> findBitcoinCredentials() {
        String token = productPaymentDataService.findBitcoinToken();

        return token == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(token);
    }
}
