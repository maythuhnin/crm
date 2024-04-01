package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.ExpenseTypeBean;

@Component
public class ExpenseTypeApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ExpenseTypeBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ExpenseTypeBean expenseType = (ExpenseTypeBean) target;
		
		if(expenseType.getMode().equals(Mode.ADD)) {
			checkIfEmpty(expenseType.getName(), "name", errors);
		}else if (expenseType.getMode().equals(Mode.EDIT)){
			checkIfEmpty(expenseType.getId(), "id", errors);
			checkIfEmpty(expenseType.getName(), "name", errors);
		}else if (expenseType.getMode().equals(Mode.DELETE)){
			checkIfEmpty(expenseType.getId(), "id", errors);	
		}
		
	}

}
