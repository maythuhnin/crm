package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.InventoryBean;

@Component
public class InventoryApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return InventoryBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		InventoryBean inventory = (InventoryBean) target;
		
		if(inventory.getMode().equals(Mode.ADD)) {
			checkIfEmpty(inventory.getItem(), "item", errors);
		}else if (inventory.getMode().equals(Mode.EDIT)){
			checkIfEmpty(inventory.getId(), "id", errors);
			checkIfEmpty(inventory.getItem(), "item", errors);
		}else if (inventory.getMode().equals(Mode.DELETE)){
			checkIfEmpty(inventory.getId(), "id", errors);	
		}
		
	}

}
