package com.example.WebShop.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserTokenState {

    private String accessToken;
    private Long expiresIn;
    private Integer userId;
    private List<String> roles;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.roles = null;
    }

}