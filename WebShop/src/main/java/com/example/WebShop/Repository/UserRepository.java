package com.example.WebShop.Repository;

import com.example.WebShop.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT a FROM User a WHERE a.username = ?1")
    User findByUsername(String username);
}
