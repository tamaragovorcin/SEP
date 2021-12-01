package com.example.api.DTOs;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String password;
    private String authority;
}