package com.BankCardService.Repository;

import com.BankCardService.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Optional<Transaction> findByMerchantOrderId(Integer orderId);

}
