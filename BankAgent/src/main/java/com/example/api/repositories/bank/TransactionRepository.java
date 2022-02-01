package com.example.api.repositories.bank;

import com.example.api.entities.bank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Optional<Transaction> findByMerchantOrderId(Integer orderId);

}
