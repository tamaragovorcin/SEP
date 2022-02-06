package com.example.api.controllers.users;

import com.example.api.DTOs.UserDTO;
import com.example.api.entities.bank.Bank;
import com.example.api.security.exceptions.ResourceConflictException;
import com.example.api.services.implementations.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody UserDTO userDTO) {
        try {
                Integer userId = userService.save(userDTO).getId();
                logger.info("Registering new user into bank");
                return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
        } catch (ResourceConflictException e) {
            logger.error("Error while registering new user");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}
