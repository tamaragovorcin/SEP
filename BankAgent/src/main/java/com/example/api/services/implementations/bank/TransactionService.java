package com.example.api.services.implementations.bank;

import com.example.api.entities.bank.Transaction;
import com.example.api.repositories.bank.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public Transaction findOne(Integer orderId) {
		Optional<Transaction> opt = transactionRepository.findByMerchantOrderId(orderId);
		
		if(!opt.isPresent()) {
			return null;
		}
		
		return opt.get();
		
	}
	
}
