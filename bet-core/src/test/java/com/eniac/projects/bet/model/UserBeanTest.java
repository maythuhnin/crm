package com.eniac.projects.bet.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.eniac.projects.bet.model.UserBean;

public class UserBeanTest {
	
	private UserBean userBean;
	
	@Before
	public void setup() {
		userBean = new UserBean();
	}
	
	@Test
	public void firstTestMethod() {
	
		userBean.setId(1);
		Assert.assertEquals(100, 100);
	}
}
