package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.DailyExpenseBean;

@Component
public class DailyExpenseApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return DailyExpenseBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		DailyExpenseBean dailyExpense = (DailyExpenseBean) target;
		
		if(dailyExpense.getMode().equals(Mode.ADD)) {
			checkIfEmpty(dailyExpense.getBusId(), "busId", errors);
		}else if (dailyExpense.getMode().equals(Mode.EDIT)){
			checkIfEmpty(dailyExpense.getId(), "id", errors);
			checkIfEmpty(dailyExpense.getBusId(), "busId", errors);
		}else if (dailyExpense.getMode().equals(Mode.DELETE)){
			checkIfEmpty(dailyExpense.getId(), "id", errors);	
		}
		
	}

}
