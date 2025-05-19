package com.eniac.projects.crm.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("=====> Session is created <=====");
		event.getSession().setMaxInactiveInterval(600 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info("=====> Session is destroyed <=====");
	}
}
