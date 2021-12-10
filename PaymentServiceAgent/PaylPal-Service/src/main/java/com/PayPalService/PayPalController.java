package com.PayPalService;
import com.PayPalService.Model.Order;
import com.PayPalService.Model.PaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(PayPalController.class);

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
                    "http://localhost:3001/#/payPalParams", order.getClientId(), order.getClientSecret());
            logger.info("Paypal payment object created.");
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    logger.info("Sending paypal api link for redirection.");
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            logger.error("Exception with creating paypal payment object. Error is: " + e );
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
                logger.info("Paypal payment has just been finished.");
                payPalService.browse("http://localhost:3001/#/payPalSuccess");
                return "success";
            }
        } catch (PayPalRESTException e) {
            logger.error("Exception with finalizing paypal payment. Error is: " + e );
            payPalService.browse("http://localhost:3001/#/payPalError");
        }
        return "error";
    }

}
