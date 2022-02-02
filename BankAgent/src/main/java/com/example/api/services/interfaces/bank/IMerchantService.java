package com.example.api.services.interfaces.bank;

import com.example.api.DTOs.AccountDTO;
import com.example.api.DTOs.CardInfoDTO;
import com.example.api.entities.bank.Account;
import com.example.api.entities.bank.Merchant;

import java.util.List;

public interface IMerchantService {
    Merchant findById(Integer id);
    List<Merchant> findAll ();
    Merchant save(CardInfoDTO dto);
    Merchant update(Merchant merchant);
    Merchant findByMerchantId(String id);
}
