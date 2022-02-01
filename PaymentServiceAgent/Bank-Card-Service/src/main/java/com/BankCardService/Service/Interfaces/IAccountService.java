package com.BankCardService.Service.Interfaces;

import com.BankCardService.Dtos.AccountDTO;

import java.util.List;

public interface IAccountService {
    Account findById(Integer id);
    List<Account> findAll ();
    Account save(AccountDTO dto);
    Account saveNoDTO(Account account);
    Account update(Account account);
}
