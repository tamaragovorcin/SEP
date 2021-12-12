package com.BankCardService.Controller;

import com.BankCardService.Dtos.CreatePayment;
import com.BankCardService.Dtos.CreatePaymentResponse;
import com.BankCardService.Dtos.NewOrderDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankcard")
public class BankCardController {


    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody List<NewOrderDTO> items) throws StripeException {

        System.out.println(items);

        Integer userId = 0;
        Double amount = 0.0;

        for(NewOrderDTO orderDTO: items){
            userId = orderDTO.getRegisteredUserId();
            amount += orderDTO.getPrice();
        }
        PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount((new Double(amount)).longValue())
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
