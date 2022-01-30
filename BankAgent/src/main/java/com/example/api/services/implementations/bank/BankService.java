package com.example.api.services.implementations.bank;

import com.example.api.DTOs.BankDTO;
import com.example.api.entities.users.Bank;
import com.example.api.repositories.users.BankRepository;
import com.example.api.services.interfaces.bank.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService implements IBankService {
    @Autowired
    BankRepository bankRepository;

    @Override
    public Bank findById(Integer id) {
        return bankRepository.findById(id).get();
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank save(BankDTO dto) {
        Bank bank =  new Bank();
        bank.setName(dto.getName());
        bank.setFirstThreeNumbers(dto.getFirstThreeNumbers());
        return bankRepository.save(bank);
    }

    @Override
    public Bank update(Bank bank) {
        return null;
    }
}
