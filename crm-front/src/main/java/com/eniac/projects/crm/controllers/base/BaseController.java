package com.eniac.projects.crm.controllers.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.UserBean;
import com.eniac.projects.crm.service.UserServiceImpl;

@Controller
public class BaseController {
	
	protected static final String MYBATIS_EXCEPTION_MSG = "Database Error has occured. Please try again.";
	
	protected static final String BUSINESS_EXCEPTION_MSG = "System Error has occured. Please try again.";
	
	@Autowired
	private UserServiceImpl userService; 

	protected UserBean getLoggedInUser() throws MyBatisException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Map<String,Object> criteria = new HashMap<String, Object>();
			criteria.put("active", 1);
			criteria.put("username", authentication.getName());
		    return userService.selectByCriteria(criteria);
		}else {
			return null;
		}
	}
	
	
}
