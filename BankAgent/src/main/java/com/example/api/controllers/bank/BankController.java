package com.example.api.controllers.bank;


import com.example.api.DTOs.*;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.Bank;
import com.example.api.entities.bank.Merchant;
import com.example.api.security.exceptions.ResourceConflictException;
import com.example.api.services.implementations.bank.AccountService;
import com.example.api.services.implementations.bank.BankService;
import com.example.api.services.implementations.bank.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BankController {
    @Autowired
    BankService bankService;
    @Autowired
    AccountService accountService;
    @Autowired
    MerchantService merchantService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody BankDTO dto) {
        try {
            Integer userId = bankService.save(dto).getId();
            return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks  = bankService.findAll();
        return banks == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(banks);
    }

    @PostMapping("/openAnAccount")
    public ResponseEntity<Account> openAnAccount(@RequestBody AccountDTO dto) {
        try {
            Account account = accountService.save(dto);
            return new ResponseEntity<Account>(account, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registerMerchant")
    public ResponseEntity<Merchant> registerMerchant(@RequestBody CardInfoDTO dto) {
        try {
            Merchant merchant= merchantService.save(dto);
            return new ResponseEntity<Merchant>(merchant, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/merchantSearch")
    public ResponseEntity<BooleanDTO> merchantSearch(@RequestBody MerchantInfoDTO dto) {
        try {
            BooleanDTO booleanDTO = new BooleanDTO();
            booleanDTO.setValid(merchantService.merchantSearch(dto));
            return new ResponseEntity<BooleanDTO>(booleanDTO, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Integer id) throws IOException {

        Bank bank = bankService.findById(id);
        return bank == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(bank);
    }

}
