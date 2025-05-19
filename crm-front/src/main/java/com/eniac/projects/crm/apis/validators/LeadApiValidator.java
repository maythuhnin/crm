package com.eniac.projects.crm.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.crm.model.LeadBean;
import com.eniac.projects.crm.model.BaseBean.Mode;

@Component
public class LeadApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return LeadBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		LeadBean lead = (LeadBean) target;
		
		if(lead.getMode().equals(Mode.ADD)) {
			checkIfEmpty(lead.getContactName(), "contactName", errors);
		}else if (lead.getMode().equals(Mode.EDIT)){
			checkIfEmpty(lead.getId(), "id", errors);
			checkIfEmpty(lead.getContactName(), "contactName", errors);
		}else if (lead.getMode().equals(Mode.DELETE)){
			checkIfEmpty(lead.getId(), "id", errors);	
		}
		
	}

}
