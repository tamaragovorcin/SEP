package com.PayPalService;
import com.PayPalService.Model.Order;
import com.PayPalService.Model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

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
                    "http://localhost:3001/#/payPalParams", order.getClientId(),order.getClientSecret());
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
            if (payment.getState().equals("approved")) {
                payPalService.savePayment(payment);
                payPalService.browse("http://localhost:3001/#/payPalSuccess");
                return "success";
            }
        } catch (PayPalRESTException e) {
            payPalService.browse("http://localhost:3001/#/payPalError");
            System.out.println(e.getMessage());
        }
        return "error";
    }

}
