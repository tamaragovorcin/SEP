package com.example.WebShop.Controllers;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.*;
import com.example.WebShop.Service.IServices.IPaymentMethodService;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.Implementations.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentMethodController {

    @Autowired
    private CartService cartService;
    @Autowired
    private IProductService productService;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addPayment(@RequestBody PaymentMethodDTO paymentMethodDTO) {

        try {
            paymentMethodService.save(paymentMethodDTO);
            logger.info("Payment methods had been added to the webshop: ");

        }
        catch (Exception e){
            logger.error("Exception while adding payment methods. Error is: " + e);

        }

        return new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PaymentMethodDTO> paymentMethods() {

        List<String> list = new ArrayList<>();
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        try {
            paymentMethods = paymentMethodService.findAll();
            for (PaymentMethod paymentMethod : paymentMethods) {
                if (paymentMethod.getMethod().toString().equals("CARD")) {

                    list.add("Card");
                }
                if (paymentMethod.getMethod().toString().equals("PAYPAL")) {

                    list.add("Paypal");
                }
                if (paymentMethod.getMethod().toString().equals("BITCOIN")) {

                    list.add("Bitcoin");
                }
                if (paymentMethod.getMethod().toString().equals("QR")) {

                    list.add("Qr");
                }
            }
            paymentMethodDTO.setMethods(list);

            logger.info("Overview of the currently choosen payment mathods for webshop" );

        }
        catch (Exception e ){

            logger.error("Exception while overviewing payment methods. Error is: " + e);
        }
        return paymentMethodDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paymentMethodDTO);
    }


}
