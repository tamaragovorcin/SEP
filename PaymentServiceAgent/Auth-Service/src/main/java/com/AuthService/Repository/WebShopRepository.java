package com.AuthService.Repository;

import com.AuthService.Model.WebShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebShopRepository extends JpaRepository<WebShop, Integer> {
    WebShop findByUsername(String username);

    WebShop findByWebShopId(Integer id);

}

