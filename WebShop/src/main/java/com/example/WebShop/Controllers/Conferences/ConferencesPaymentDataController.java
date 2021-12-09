package com.example.WebShop.Controllers.Conferences;

import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Service.IServices.Conferences.IConferencePaymentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/conference/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConferencesPaymentDataController {

    @Autowired
    IConferencePaymentDataService conferencePaymentDataService;

    @GetMapping("/paypal")
    public ResponseEntity<PaypalCredentialsDTO> findPaypalCredentials() {
        PaypalCredentialsDTO paypalCredentialsDTO = conferencePaymentDataService.findPaypalCredentials();

        return paypalCredentialsDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paypalCredentialsDTO);
    }

    @GetMapping("/bitcoin")
    public ResponseEntity<String> findBitcoinCredentials() {
        String token = conferencePaymentDataService.findBitcoinToken();

        return token == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(token);
    }
}
