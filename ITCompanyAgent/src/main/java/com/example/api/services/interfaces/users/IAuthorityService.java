package com.example.api.services.interfaces.users;

import com.example.api.DTOs.AuthorityDTO;
import com.example.api.entities.users.Authority;

public interface IAuthorityService {
    Authority findById(Integer id);
    Authority findByname(String name);
    Authority save(AuthorityDTO dto);
}
