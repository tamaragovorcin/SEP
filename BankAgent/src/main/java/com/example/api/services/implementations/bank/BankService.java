package com.example.api.services.implementations.bank;

import com.example.api.DTOs.BankDTO;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.Bank;
import com.example.api.entities.bank.Merchant;
import com.example.api.repositories.bank.AccountRepository;
import com.example.api.repositories.bank.BankRepository;
import com.example.api.repositories.bank.MerchantRepository;
import com.example.api.services.interfaces.bank.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class BankService implements IBankService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MerchantRepository merchantRepository;

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
        bankRepository.save(bank);
        if(bank.getFirstThreeNumbers().equals("111")) {
            Account account = new Account();
            account.setBank(bank);
            account.setAvailableFunds(1000000.00);
            account.setReservedFunds(0.0);
            account.setGiroNumber("123456789");
            account.setPAN("123456789");
            account.setExpirationDate(YearMonth.now().plusYears(5));
            account.setCardSecurityCode("123");
            account.setCardHolderUCIN("123456789");
            account.setCardHolderName("IT COMPANY");
            account.setReferenceNumber("123456789");
            accountRepository.save(account);
            Merchant merchant = new Merchant();
            merchant.setMerchantId("123456789");
            merchant.setMerchantPassword("123456789");
            merchant.setAccount(account);
            merchantRepository.save(merchant);
        }
        return bankRepository.save(bank);
    }

    @Override
    public Bank update(Bank bank) {
        return null;
    }
}
