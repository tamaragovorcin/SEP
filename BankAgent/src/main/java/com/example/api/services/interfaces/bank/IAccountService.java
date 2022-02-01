package com.example.api.services.interfaces.bank;
import com.example.api.DTOs.AccountDTO;
import com.example.api.entities.bank.Account;

import java.util.List;

public interface IAccountService {
    Account findById(Integer id);
    List<Account> findAll ();
    Account save(AccountDTO dto);
    Account saveNoDTO(Account account);
    Account update(Account account);
}
