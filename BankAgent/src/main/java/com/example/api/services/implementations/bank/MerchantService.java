package com.example.api.services.implementations.bank;

import com.example.api.DTOs.CardInfoDTO;
import com.example.api.DTOs.MerchantInfoDTO;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.Merchant;
import com.example.api.repositories.bank.AccountRepository;
import com.example.api.repositories.bank.MerchantRepository;
import com.example.api.services.interfaces.bank.IMerchantService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
public class MerchantService implements IMerchantService {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant findById(Integer id) {
        return merchantRepository.findById(id).get();
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    public Merchant saveNoDTO(Merchant dto) {
        return merchantRepository.save(dto);
    }

    @Override
    public Merchant save(CardInfoDTO dto) {
        Merchant merchant = new Merchant();
        if(accountService.checkCardInfo(dto)){
            System.out.println("Prosaaoooo");
            int leftLimit = 48;
            int rightLimit = 57;
            int targetStringLength = 6;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            merchant.setMerchantId(generatedString);

            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).toCharArray();
            String randomStr = RandomStringUtils.random( 25, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
            merchant.setMerchantPassword(randomStr);
            Account account = accountService.findByPan(dto.getPan());
            merchant.setAccount(account);
            merchant =  merchantRepository.save(merchant);
            account.setMerchant(merchant);
            accountRepository.save(account);
            return  merchant;
        }
        return null;
    }

    @Override
    public Merchant update(Merchant merchant) {
        return null;
    }

    @Override
    public Merchant findByMerchantId(String id) {
        return merchantRepository.findByMerchantId(id);
    }

    public Boolean merchantSearch(MerchantInfoDTO dto) {
        for(Merchant merchant : findAll()){
            if(merchant.getMerchantId().equals(dto.getMerchantID())&&merchant.getMerchantPassword().equals(dto.getMerchantPassword())&&merchant.getAccount().getBank().getId() == dto.getBank()){
                return true;
            }
        }
        return false;
    }


}
