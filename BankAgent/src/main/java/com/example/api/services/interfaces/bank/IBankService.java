package com.example.api.services.interfaces.bank;

import com.example.api.DTOs.BankDTO;
import com.example.api.entities.bank.Bank;

import java.util.List;

public interface IBankService {
    Bank findById(Integer id);
    List<Bank> findAll ();
    Bank save(BankDTO dto);
    Bank update(Bank bank);
}
