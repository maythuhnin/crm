package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.DriverBean;

@Component
public class DriverApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return DriverBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		DriverBean driver = (DriverBean) target;
		
		if(driver.getMode().equals(Mode.ADD)) {
			checkIfEmpty(driver.getName(), "name", errors);
		}else if (driver.getMode().equals(Mode.EDIT)){
			checkIfEmpty(driver.getId(), "id", errors);
			checkIfEmpty(driver.getName(), "licensePlate", errors);
		}else if (driver.getMode().equals(Mode.DELETE)){
			checkIfEmpty(driver.getId(), "id", errors);	
		}
		
	}

}
