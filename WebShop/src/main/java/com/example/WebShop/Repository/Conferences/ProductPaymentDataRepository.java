package com.example.WebShop.Repository;

import com.example.WebShop.Model.ProductPaymentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductPaymentDataRepository extends JpaRepository<ProductPaymentData, Integer>{

    @Query(value = "SELECT a FROM ProductPaymentData a WHERE a.id = 1")
    ProductPaymentData findProductPaymentData();

}
