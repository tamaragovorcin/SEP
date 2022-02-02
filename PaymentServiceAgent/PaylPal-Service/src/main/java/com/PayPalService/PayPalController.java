package com.PayPalService;
import com.PayPalService.DTO.UrlAddressesDTO;
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

    @PostMapping("/pay")
    public String payment(@RequestBody Order order) {
        UrlAddressesDTO urls = payPalService.getUrlAddressByWebShopId(order.getWebShopId());
        try {
            Payment payment = payPalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), urls.getFailureUrl(),
                    "http://localhost:3000/#/payPalParams", order.getClientId(), order.getClientSecret());
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
        return urls.getErrorUrl();
    }

    @PostMapping("/success")
    public String successPay(@RequestBody PaymentInfo paymentInfo) {
        UrlAddressesDTO urls = payPalService.getUrlAddressByWebShopId(paymentInfo.getWebShopId());

        try {
            Payment payment = payPalService.executePayment(paymentInfo.getPaymentId(), paymentInfo.getPayerId());
            if (payment.getState().equals("approved")) {
                payPalService.savePayment(payment);
                logger.info("Paypal payment has just been finished.");
                payPalService.browse(urls.getSuccessUrl());
                return "success";
            }
        } catch (PayPalRESTException e) {
            logger.error("Exception with finalizing paypal payment. Error is: " + e );
            payPalService.browse(urls.getErrorUrl());
        }
        return "error";
    }

}
