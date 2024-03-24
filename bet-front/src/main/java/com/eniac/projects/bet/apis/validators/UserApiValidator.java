package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.UserBean;

@Component
public class UserApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserBean user = (UserBean) target;
		
		if(user.getMode().equals(Mode.ADD)) {
			checkIfEmpty(user.getName(), "name", errors);
			checkIfEmpty(user.getUsername(), "username", errors);
			checkIfEmpty(user.getPassword(), "password", errors);
			checkIfEmpty(user.getRole(), "role", errors);
		}else if (user.getMode().equals(Mode.EDIT)){
			checkIfEmpty(user.getId(), "id", errors);
			checkIfEmpty(user.getName(), "name", errors);
			checkIfEmpty(user.getUsername(), "username", errors);
			checkIfEmpty(user.getRole(), "role", errors);		
		}else if (user.getMode().equals(Mode.DELETE)){
			checkIfEmpty(user.getId(), "id", errors);	
		}else if (user.getMode().equals(Mode.PASSWORD)){
			checkIfEmpty(user.getId(), "id", errors);	
			checkIfEmpty(user.getPassword(), "password", errors);
			checkIfEmpty(user.getNewPassword(), "newPassword", errors);
		}
		
	}

}
