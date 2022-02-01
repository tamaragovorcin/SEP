package com.example.api.repositories.bank;

import com.example.api.entities.bank.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByPanNumber(String panNumber);
    Optional<Account> findByMerchantId(String merchantId);
}
