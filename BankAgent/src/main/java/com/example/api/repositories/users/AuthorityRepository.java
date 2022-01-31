package com.example.api.repositories.users;

import com.example.api.entities.users.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(String name);
}