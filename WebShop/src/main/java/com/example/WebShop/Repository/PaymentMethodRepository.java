package com.example.WebShop.Repository;

import com.example.WebShop.Model.PaymentMethod;
import com.example.WebShop.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
