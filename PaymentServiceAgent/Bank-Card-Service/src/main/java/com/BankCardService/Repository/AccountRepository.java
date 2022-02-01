package com.BankCardService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByPAN(String panNumber);
    Optional<Account> findByMerchantId(String merchantId);
}
