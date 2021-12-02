package com.example.api.services.interfaces.users;

import com.example.api.DTOs.UserDTO;
import com.example.api.entities.users.User;
import java.util.List;

public interface IUserService {
    User findById(Integer id);
    List<User> findAll ();
    User save(UserDTO userDTO);
    User update(User user);
}
