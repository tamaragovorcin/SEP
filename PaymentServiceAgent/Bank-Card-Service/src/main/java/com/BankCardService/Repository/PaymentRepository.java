package com.BankCardService.Repository;

import com.BankCardService.Model.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentRequest, Integer> {

}
