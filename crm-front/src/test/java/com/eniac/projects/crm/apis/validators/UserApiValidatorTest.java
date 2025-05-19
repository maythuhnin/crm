package com.eniac.projects.crm.apis.validators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.eniac.projects.crm.apis.validators.UserApiValidator;
import com.eniac.projects.crm.config.AppConfig;
import com.eniac.projects.crm.config.TilesConfig;
import com.eniac.projects.crm.config.WebSecurityConfig;
import com.eniac.projects.crm.model.UserBean;
import com.eniac.projects.crm.model.BaseBean.Mode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TilesConfig.class, WebSecurityConfig.class})
@WebAppConfiguration
public class UserApiValidatorTest {

	@Autowired
	UserApiValidator userApiValidator;

	@Test
	public void testUserADD_InvalidValues() throws Exception {
		UserBean user = new UserBean();
		user.setMode(Mode.ADD);
		
		BindException bindException = new BindException(user, "user");
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		//Assert
		Assert.assertEquals(4, bindException.getErrorCount());
	}
	
	@Test
	public void testUserADD_ValidValues() throws Exception {
		UserBean user = new UserBean();
		user.setName("User");
		user.setUsername("user");
		user.setPassword("P@ssw0rd");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.ADD);
		
		BindException bindException = new BindException(user, "user");
		
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		
		//Assert
		Assert.assertEquals(0, bindException.getErrorCount());
	}
	
	@Test
	public void testUserEDIT_InvalidValues() throws Exception {
		UserBean user = new UserBean();
		user.setMode(Mode.EDIT);
		
		BindException bindException = new BindException(user, "user");
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		System.err.println(bindException.getAllErrors());
		
		//Assert
		Assert.assertEquals(4, bindException.getErrorCount());
	}
	
	@Test
	public void testUserEDIT_ValidValues() throws Exception {
		UserBean user = new UserBean();
		user.setId(1);
		user.setName("User");
		user.setUsername("user");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.EDIT);
		
		BindException bindException = new BindException(user, "user");
		
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		//Assert
		Assert.assertEquals(0, bindException.getErrorCount());
	}
	
	@Test
	public void testUserDELETE_InvalidValues() throws Exception {
		UserBean user = new UserBean();
		user.setMode(Mode.DELETE);
		
		BindException bindException = new BindException(user, "user");
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		//Assert
		Assert.assertEquals(1, bindException.getErrorCount());
	}
	
	@Test
	public void testUserDELETE_ValidValues() throws Exception {
		UserBean user = new UserBean();
		user.setId(1);
		user.setMode(Mode.DELETE);
		
		BindException bindException = new BindException(user, "user");
		
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		
		//Assert
		Assert.assertEquals(0, bindException.getErrorCount());
	}
	
	@Test
	public void testUserPASSWORD_InvalidValues() throws Exception {
		UserBean user = new UserBean();
		user.setMode(Mode.PASSWORD);
		
		BindException bindException = new BindException(user, "user");
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		//Assert
		Assert.assertEquals(3, bindException.getErrorCount());
	}
	
	@Test
	public void testUserPASSWORD_ValidValues() throws Exception {
		UserBean user = new UserBean();
		user.setId(1);
		user.setPassword("P@ssw0rd");
		user.setNewPassword("New P@ssw0rd");
		user.setMode(Mode.DELETE);
		
		BindException bindException = new BindException(user, "user");
		
		//Act
		ValidationUtils.invokeValidator(userApiValidator, user, bindException);
		
		//Assert
		Assert.assertEquals(0, bindException.getErrorCount());
	}
}
