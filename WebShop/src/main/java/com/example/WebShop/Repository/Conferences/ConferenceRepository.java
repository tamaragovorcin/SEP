package com.example.WebShop.Repository.Conferences;

import com.example.WebShop.Model.Conferences.Conference;
import com.example.WebShop.Model.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
}
