package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.DestinationBean;

@Component
public class DestinationApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return DestinationBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		DestinationBean destination = (DestinationBean) target;
		
		if(destination.getMode().equals(Mode.ADD)) {
			checkIfEmpty(destination.getName(), "name", errors);
		}else if (destination.getMode().equals(Mode.EDIT)){
			checkIfEmpty(destination.getId(), "id", errors);
			checkIfEmpty(destination.getName(), "name", errors);
		}else if (destination.getMode().equals(Mode.DELETE)){
			checkIfEmpty(destination.getId(), "id", errors);	
		}
		
	}

}
