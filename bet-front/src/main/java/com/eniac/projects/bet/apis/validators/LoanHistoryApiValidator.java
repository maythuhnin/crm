package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.LoanHistoryBean;

@Component
public class LoanHistoryApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return LoanHistoryBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		LoanHistoryBean loanHistory = (LoanHistoryBean) target;
		
		if(loanHistory.getMode().equals(Mode.ADD)) {
			checkIfEmpty(loanHistory.getLoanDate(), "loanDate", errors);
		}else if (loanHistory.getMode().equals(Mode.DELETE)){
			checkIfEmpty(loanHistory.getId(), "id", errors);	
		}
		
	}

}
