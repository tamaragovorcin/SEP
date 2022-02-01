package com.BankCardService.Controller;

import com.BankCardService.Dtos.CreatePayment;
import com.BankCardService.Dtos.CreatePaymentResponse;
import com.BankCardService.Dtos.NewOrderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankCardController {



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
