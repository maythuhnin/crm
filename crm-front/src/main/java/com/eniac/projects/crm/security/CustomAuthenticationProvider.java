package com.eniac.projects.crm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eniac.projects.crm.exception.BuisnessException;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.UserBean;
import com.eniac.projects.crm.service.UserServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	JwtUserDetailsService jwtUserService;
	
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final String username = authentication.getName();
		final String password = encoder.encode(authentication.getCredentials().toString());
		UsernamePasswordAuthenticationToken token = null;
		List<SimpleGrantedAuthority> userRoles;
		try {
			userRoles = getUserIfAuthenticated(authentication);
			token = new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(userRoles));
			
			if (null != token) {
				
			}
		} catch (MyBatisException | BuisnessException e) {
			e.printStackTrace();
		}

		return token;
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(final List<SimpleGrantedAuthority> roles) {
		return roles.stream().map(role -> (GrantedAuthority) role::getAuthority).collect(Collectors.toList());
	}

	private List<SimpleGrantedAuthority> getUserIfAuthenticated(final Authentication authentication)
			throws MyBatisException, BuisnessException {

		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("username", authentication.getName());
		final UserBean user = userService.selectByCriteria(criteria);

		if (user == null) {
			throw new UsernameNotFoundException("Authentication failed");
		} else {

		}

		if (!encoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
			throw new UsernameNotFoundException("Authentication failed");
		} else {
			userService.updateLoggedIn(user.getId());

		}

		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

		return grantedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
