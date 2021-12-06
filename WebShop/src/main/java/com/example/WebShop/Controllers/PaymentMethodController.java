package com.example.WebShop.Controllers;


import com.example.WebShop.DTOs.NewOrderDTO;
import com.example.WebShop.DTOs.NewProductDTO;
import com.example.WebShop.DTOs.PaymentMethodDTO;
import com.example.WebShop.Model.*;
import com.example.WebShop.Service.IServices.IPaymentMethodService;
import com.example.WebShop.Service.IServices.IProductService;
import com.example.WebShop.Service.Implementations.CartService;
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

    @PostMapping("/add")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> addReservation(@RequestBody PaymentMethodDTO paymentMethodDTO) {

        paymentMethodService.save(paymentMethodDTO);

        return new ResponseEntity<>("Item is successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    // @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<PaymentMethodDTO> hats() {

        List<String> list = new ArrayList<>();
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

        for(PaymentMethod paymentMethod: paymentMethods){
            if(paymentMethod.getMethod().toString().equals("CARD")){

                list.add("Card");
            }
            if(paymentMethod.getMethod().toString().equals("PAYPAL")){

                list.add("Paypal");
            }
            if(paymentMethod.getMethod().toString().equals("BITCOIN")){

                list.add("Bitcoin");
            }
            if(paymentMethod.getMethod().toString().equals("QR")){

                list.add("Qr");
            }
        }
        paymentMethodDTO.setMethods(list);

        return paymentMethodDTO == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(paymentMethodDTO);
    }


}
