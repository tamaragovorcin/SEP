package com.example.api.services.implementations.bank;

import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.CardInfoDTO;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.Bank;
import com.example.api.repositories.bank.AccountRepository;
import com.example.api.services.interfaces.bank.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BankService bankService;

    @Override
    public Account findById(Integer id) {
        return accountRepository.getOne(id);
    }

    public Optional<Account> getClient(String panNumber) {
        return accountRepository.findByPAN(panNumber);
    }
    public Optional<Account> getClientByGiroNumber(String num) {
        return accountRepository.findByGiroNumber(num);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(AccountDTO dto) {
        Account account = new Account();
        account.setCardHolderName(dto.getCardHolderName());
        account.setCardHolderUCIN(dto.getCardHolderUCIN());
        account.setAvailableFunds(5000.00);
        account.setReservedFunds(0.00);
        Bank bank = bankService.findById(dto.getBankId());
        account.setBank(bank);
        int leftLimit1 = 48;
        int rightLimit1 = 57;
        int targetStringLength1 = 3;
        Random random1 = new Random();
        StringBuilder buffer1 = new StringBuilder(targetStringLength1);
        for (int i = 0; i < targetStringLength1; i++) {
            int randomLimitedInt = leftLimit1 + (int)
                    (random1.nextFloat() * (rightLimit1 - leftLimit1 + 1));
            buffer1.append((char) randomLimitedInt);
        }
        String generatedString1 = buffer1.toString();
        account.setCardSecurityCode(generatedString1);

        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = bank.getFirstThreeNumbers().toString()+ buffer.toString();

        account.setPAN(generatedString);


        int leftLimit2 = 48; // letter 'a'
        int rightLimit2 = 57; // letter 'z'
        int targetStringLength2 = 14;
        Random random2 = new Random();
        StringBuilder buffer2 = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength2; i++) {
            int randomLimitedInt2 = leftLimit2 + (int)
                    (random2.nextFloat() * (rightLimit2 - leftLimit2 + 1));
            buffer2.append((char) randomLimitedInt2);
        }
        String generatedString2 = bank.getFirstThreeNumbers().toString()+ buffer2.toString();
        account.setGiroNumber(generatedString2);


        Random random3 = new Random();
        StringBuilder buffer3 = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength2; i++) {
            int randomLimitedInt2 = leftLimit2 + (int)
                    (random3.nextFloat() * (rightLimit2 - leftLimit2 + 1));
            buffer3.append((char) randomLimitedInt2);
        }
        String generatedString3 = bank.getFirstThreeNumbers().toString()+ buffer3.toString();
        account.setReferenceNumber(generatedString3);


        account.setExpirationDate(YearMonth.now().plusYears(5));
        return accountRepository.save(account);
    }

    @Override
    public Account saveNoDTO(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    public boolean checkCardInfo(CardInfoDTO dto) {
        System.out.println("EXPIRATION DATE:");
        System.out.println(dto.getExpirationDate());
        for(Account account : findAll()){
            System.out.println("EXPRICATION DATE IZ BAZE:");
            System.out.println(account.getExpirationDate());
            System.out.println(account.getExpirationDate().equals(dto.getExpirationDate()));
            if(account.getPAN().equals(dto.getPan())
                    && account.getCardHolderName().equals(dto.getCardHolderName())
                    && account.getCardSecurityCode().equals(dto.getCardSecurityCode())
                    && account.getExpirationDate().equals(dto.getExpirationDate())){
                return  true;
            }
        }
        return false;
    }
    public Account findByPan(String pan) {
        for(Account account : findAll()){
            if(account.getPAN().equals(pan)){
                return  account;
            }
        }
        return null;
    }

    public Account getClientByMerchantId(String merchantId){
        Optional<Account> client = accountRepository.findByMerchantId(merchantId);
        if(!client.isPresent()) {
            // TODO: ako ne postoji transakcija nije validna idi na faild url
            return null;
        }
        return client.get();
    }
}