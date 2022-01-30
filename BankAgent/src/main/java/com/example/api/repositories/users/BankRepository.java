package com.example.api.repositories.users;

import com.example.api.entities.users.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Integer> {

}
