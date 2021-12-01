package com.example.api.repositories.users;

import com.example.api.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
   User findByEmail(String email);
}
