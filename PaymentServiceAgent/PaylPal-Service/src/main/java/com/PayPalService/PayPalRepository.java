package com.PayPalService;

import com.PayPalService.Model.PayPalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayPalRepository extends JpaRepository<PayPalPayment, Integer>{
}
