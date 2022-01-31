package com.example.api.repositories.bank;

import com.example.api.entities.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Integer> {

}
