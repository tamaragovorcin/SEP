package com.BankCardService.Controller;

import com.BankCardService.Dtos.CreatePayment;
import com.BankCardService.Dtos.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankcard")
public class BankCardController {


    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {


        PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(15 * 100L)
                            .setCurrency("usd")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods
                                            .builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return new CreatePaymentResponse(paymentIntent.getClientSecret());


    }
    @GetMapping("")
    public String home() {
        return "bank card home" ;
    }


    @GetMapping("/checkout")
    public String checkout() {
        return "bank card home" ;
    }

    @GetMapping("/getByName/{name}")
    public String getByName(@PathVariable String name) {
        return "bank card home -" + name;
    }
}
