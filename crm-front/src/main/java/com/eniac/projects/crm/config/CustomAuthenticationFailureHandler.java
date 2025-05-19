package com.eniac.projects.crm.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomAuthenticationFailureHandler() {
		super();
	}

	// API

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String redirectURL = "/login";
		
		if(exception.toString().contains("Authentication failed")) {
			redirectURL = "/login?errorType=user";
		}else if(exception.toString().contains("No subscription.") ) {
			redirectURL = "/login?errorType=noSub";
		}else if(exception.toString().contains("Subscription expired.") ) {
			redirectURL = "/login?errorType=subEnd";
		}else if(exception.toString().contains("Subscription not started.")) {
			redirectURL = "/login?errorType=subStart";
		}
		
		 redirectStrategy.sendRedirect(request, response, redirectURL);	
	}

}
