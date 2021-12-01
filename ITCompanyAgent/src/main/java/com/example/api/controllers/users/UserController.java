package com.example.api.controllers.users;

import com.example.api.DTOs.UserDTO;
import com.example.api.security.exceptions.ResourceConflictException;
import com.example.api.services.implementations.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody UserDTO userDTO) {
        try {
                Integer userId = userService.save(userDTO).getId();
                return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
