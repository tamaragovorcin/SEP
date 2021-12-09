package com.example.WebShop.Controllers;


import com.example.WebShop.Authentication.JwtAuthenticationRequest;
import com.example.WebShop.Authentication.TokenUtils;
import com.example.WebShop.Model.User;
import com.example.WebShop.Model.UserTokenState;
import com.example.WebShop.Service.Implementations.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsServiceImpl userDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Value("36500")
	private int EXPIRES;

	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
																	HttpServletResponse response) {


		List<String> roles = new ArrayList<String>();
		String jwt;
		int expiresIn;
		Integer id;
		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			User user = (User) authentication.getPrincipal();
			jwt = tokenUtils.generateToken(user.getUsername());
			expiresIn = tokenUtils.getExpiredIn();
			user.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
			id= user.getId();
			logger.info("User " + user.getUsername() + " has just logged in.");
		}
		catch (Exception e) {
			logger.error("Exception while logging in as a user " + authenticationRequest.getUsername() +" " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, (long) expiresIn, id, roles), HttpStatus.OK);
	}

}


