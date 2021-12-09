package com.BitCoinService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bitcoin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BitCoinController {

    @GetMapping("")
    public String home() {
        return "bitcoin home";
    }
}
