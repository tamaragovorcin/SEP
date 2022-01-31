package com.example.api.repositories.bank;

import com.example.api.entities.bank.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
