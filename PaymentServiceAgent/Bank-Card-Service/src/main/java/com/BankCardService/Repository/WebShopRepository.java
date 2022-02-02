package com.BankCardService.Repository;

import com.BankCardService.Model.WebShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebShopRepository extends JpaRepository<WebShop, Integer> {
    WebShop findByUsername(String username);
    WebShop findByWebShopId(Integer id);

}

