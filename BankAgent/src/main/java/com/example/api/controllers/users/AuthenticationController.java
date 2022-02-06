package com.example.api.controllers.users;

import com.example.api.DTOs.UserTokenStateDTO;
import com.example.api.entities.users.User;
import com.example.api.repositories.users.UserRepository;
import com.example.api.security.JwtAuthenticationRequest;
import com.example.api.security.TokenUtils;
import com.example.api.services.implementations.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) {
        System.out.println("Pogodio");
        String jwt;
        int expiresIn;
        List<String> roles = new ArrayList<String>();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            jwt = tokenUtils.generateToken(user.getUsername());
            expiresIn = tokenUtils.getExpiredIn();
            user.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
            user.setName("Mladenka");
            userRepository.save(user);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<UserTokenStateDTO>(new UserTokenStateDTO(jwt, expiresIn, roles), HttpStatus.OK);
    }


  /*  @PostMapping("/change-password")
    @PreAuthorize("*")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {

        try {
            userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/



    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }




}