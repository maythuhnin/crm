package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.PathBean;

@Component
public class PathApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return PathBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		PathBean path = (PathBean) target;
		
		if(path.getMode().equals(Mode.ADD)) {
			checkIfEmpty(path.getPath(), "path", errors);
		}else if (path.getMode().equals(Mode.EDIT)){
			checkIfEmpty(path.getId(), "id", errors);
			checkIfEmpty(path.getPath(), "path", errors);
		}else if (path.getMode().equals(Mode.DELETE)){
			checkIfEmpty(path.getId(), "id", errors);	
		}
		
	}

}
