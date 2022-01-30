package com.example.api.controllers.bank;


import com.example.api.DTOs.BankDTO;
import com.example.api.DTOs.UserDTO;
import com.example.api.security.exceptions.ResourceConflictException;
import com.example.api.services.implementations.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BankController {
    @Autowired
    BankService bankService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody BankDTO dto) {
        try {
            Integer userId = bankService.save(dto).getId();
            return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
