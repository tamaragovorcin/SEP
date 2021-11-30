package com.BankCardService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankcard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BankCardController {

    @GetMapping("")
    public String home() {
        return "bank card home" ;
    }

    @GetMapping("/getByName/{name}")
    public String getByName(@PathVariable String name) {
        return "bank card home -" + name;
    }
}
