package com.example.WebShop.DTOs;

import com.example.WebShop.Model.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private String email;

	private String password;

    private List<Authority> authorities;


}
