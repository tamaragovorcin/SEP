package com.example.api.services.implementations.users;

import com.example.api.DTOs.AuthorityDTO;
import com.example.api.entities.users.Authority;
import com.example.api.repositories.users.AuthorityRepository;
import com.example.api.services.interfaces.users.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService implements IAuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findById(Integer id) {
        return this.authorityRepository.getOne(id);
    }

    @Override
    public Authority findByname(String name) {
        return this.authorityRepository.findByName(name);
    }
    @Override
    public Authority save(AuthorityDTO authorityDTO) {
        Authority authority = new Authority();
        authority.setName(authorityDTO.getName());
        return authorityRepository.save(authority);
    }
    public List<Authority> resolveAuthority(String role) {
        Authority authority = findByname(role);
        List<Authority> auth = new ArrayList<Authority>();
        if(authority==null) {
            save(new AuthorityDTO(role));
            auth.add(findByname(role));
        }
        else auth.add(authority);
        return auth;
    }


}
