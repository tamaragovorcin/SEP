package com.example.api.repositories.bank;

import com.example.api.entities.bank.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentRequest, Integer> {

}
