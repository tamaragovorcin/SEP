package com.PayPalService;
import com.PayPalService.Model.Order;
import com.PayPalService.Model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/paypal")
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

    @PostMapping("/pay")
    public String payment(@RequestBody Order order) {
        try {
            Payment payment = payPalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:3001/#/payPalError",
                    "http://localhost:3001/#/payPalParams");
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "http://localhost:3001/#/payPalError";
    }

    @PostMapping("/success")
    public String successPay(@RequestBody PaymentInfo paymentInfo) {
        try {
            Payment payment = payPalService.executePayment(paymentInfo.getPaymentId(), paymentInfo.getPayerId());
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                payPalService.savePayment(payment);
                browse("http://localhost:3001/#/payPalSuccess");
                 return "success";
            }
        } catch (PayPalRESTException e) {
            browse("http://localhost:3001/#/payPalError");
            System.out.println(e.getMessage());
        }
        return "error";
    }
    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
