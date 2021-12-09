package com.example.WebShop.Repository.Conferences;

import com.example.WebShop.Model.Conferences.ConferencesCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceCartRepository extends JpaRepository<ConferencesCart, Integer> {
}
