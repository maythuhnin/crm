package com.eniac.projects.bet.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.UserBean;
import com.eniac.projects.bet.service.UserServiceImpl;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("username", username);
		
		try {
			UserBean user = userService.selectByCriteria(criteria);

			if (null != user) {
				return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}

		} catch (MyBatisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
