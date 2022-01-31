package com.example.api.entities.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private List<String> roles;
}
