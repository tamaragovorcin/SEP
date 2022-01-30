package com.example.WebShop.Repository;

import com.example.WebShop.Model.Purchase;
import com.example.WebShop.Model.WebShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreateWebShopRepository extends JpaRepository<WebShop, Integer> {
}
