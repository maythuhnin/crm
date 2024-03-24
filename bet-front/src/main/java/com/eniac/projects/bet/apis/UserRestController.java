package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.UserApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.UserBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.UserServiceImpl;

@RestController
public class UserRestController extends BaseController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	UserApiValidator userApiValidator;

	@GetMapping("/user/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForUsersDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", userService.selectForDatatable());
		return results;
	}

	@PostMapping("/user/api/add")
	public Map<String, Object> addUser(@RequestBody UserBean user, HttpServletResponse response) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			user.setMode(Mode.ADD);
			
			BindException bindException = new BindException(user, "user");
			
			ValidationUtils.invokeValidator(userApiValidator, user, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			System.err.println(checkUsernameUnique(user));
			if (!checkUsernameUnique(user)) {
				userService.createUser(user);
				response.sendError(200);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			} else {
				response.sendError(500);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
				results.put("error", "Username already exist. Please pick a different one.");
			}

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/user/api/edit")
	public Map<String, Object> editUser(@RequestBody UserBean user, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			user.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(user, "user");
			
			ValidationUtils.invokeValidator(userApiValidator, user, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			if (!checkUsernameUnique(user)) {
				userService.updateUser(user);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			} else {
				response.sendError(500);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
				results.put("error", "Username already exist. Please pick a different one.");
			}

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/user/api/password")
	public Map<String, Object> updatePassword(@RequestBody UserBean user, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			user.setId(getLoggedInUser().getId());

			user.setMode(Mode.PASSWORD);
			
			BindException bindException = new BindException(user, "user");
			
			ValidationUtils.invokeValidator(userApiValidator, user, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

			UserBean oldUser = userService.selectById(user.getId());

			if (encoder.matches(user.getPassword(), oldUser.getPassword())) {
				userService.updatePassword(user);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			} else {
				response.sendError(500);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
				results.put("error", "Wrong password.");
			}

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("user", user);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/user/api/delete")
	public Map<String, Object> deleteUser(@RequestBody Integer userId, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			UserBean user = new UserBean();
			user.setMode(Mode.DELETE);
			user.setId(userId);
			
			BindException bindException = new BindException(user, "user");
			
			ValidationUtils.invokeValidator(userApiValidator, user, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("user", user);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			userService.deleteUser(userId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("userId", userId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("userId", userId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	private boolean checkUsernameUnique(UserBean user) throws MyBatisException {

		boolean isExist = false;
		Map<String, Object> criteria = new HashMap<String, Object>();

		criteria.put("username", user.getUsername());

		if (user.getId() != 0) {
			criteria.put("notId", user.getId());
		}

		UserBean selectedUser = userService.selectByCriteria(criteria);

		if (null != selectedUser) {
			isExist = true;
		}

		return isExist;
	}
}
