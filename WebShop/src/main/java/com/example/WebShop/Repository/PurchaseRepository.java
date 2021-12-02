package com.example.WebShop.Repository;

import com.example.WebShop.Model.Cart;
import com.example.WebShop.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}
