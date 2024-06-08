package com.eniac.projects.bet.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.model.UserBean;
import com.eniac.projects.bet.security.JwtUserDetailsService;
import com.eniac.projects.bet.security.TokenManager;

@RestController
public class MobileLoginRestController extends BaseController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userService;

	@Autowired
	private TokenManager jwtUtil;

	@PostMapping("/mobile/login")
	public ResponseEntity<String> loginUser(@RequestBody UserBean request)  {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
		String token = jwtUtil.generateJwtToken(userDetails);
		
		System.err.println("Request :" + request);
		System.err.println("Token :" + token);

		return ResponseEntity.ok(token);
	}

	
	
}
