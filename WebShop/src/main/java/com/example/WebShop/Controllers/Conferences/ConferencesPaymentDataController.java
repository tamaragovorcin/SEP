package com.example.WebShop.Controllers.Conferences;

import com.example.WebShop.Controllers.ProductPaymentDataController;
import com.example.WebShop.DTOs.PaypalCredentialsDTO;
import com.example.WebShop.Service.IServices.Conferences.IConferencePaymentDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductPaymentDataController.class);

    @GetMapping("/paypal")
    public ResponseEntity<PaypalCredentialsDTO> findPaypalCredentials() {
        PaypalCredentialsDTO paypalCredentialsDTO = conferencePaymentDataService.findPaypalCredentials();
        logger.info("Getting merchant paypal credentials for conference purchase.");

        return paypalCredentialsDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paypalCredentialsDTO);
    }

    @GetMapping("/bitcoin")
    public ResponseEntity<String> findBitcoinCredentials() {
        String token = conferencePaymentDataService.findBitcoinToken();
        logger.info("Getting merchant bitcoin credentials for conference purchase.");

        return token == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(token);
    }
}
