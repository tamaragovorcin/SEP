package com.example.api.repositories.bank;

import com.example.api.entities.bank.Merchant;
import com.example.api.entities.bank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
    Merchant findByMerchantId(String id);
}
