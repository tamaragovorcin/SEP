package com.example.api.repositories.bank;

import com.example.api.entities.bank.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
