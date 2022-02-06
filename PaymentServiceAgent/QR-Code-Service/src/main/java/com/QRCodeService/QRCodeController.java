package com.QRCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @GetMapping("")
    public String home() {
        return "qr code home";
    }

    @GetMapping("/merchantPAN/{webShopId}")
    private ResponseEntity<?> createPayment(@PathVariable String webShopId) {

        String pan = qrCodeService.getMerchantPAN(webShopId);

        return new ResponseEntity<>(pan, HttpStatus.OK);
    }
}
