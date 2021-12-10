package com.example.WebShop.Repository.Conferences;

import com.example.WebShop.Model.Conferences.ConferencesPaymentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConferencesPaymentDataRepository extends JpaRepository<ConferencesPaymentData, Integer> {

    @Query(value = "SELECT a FROM ConferencesPaymentData a WHERE a.id = 1")
    ConferencesPaymentData findConferencesPaymentData();
}
