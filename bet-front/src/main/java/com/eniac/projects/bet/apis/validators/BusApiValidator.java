package com.eniac.projects.bet.apis.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.model.BusBean;

@Component
public class BusApiValidator extends CommonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BusBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		BusBean bus = (BusBean) target;
		
		if(bus.getMode().equals(Mode.ADD)) {
			checkIfEmpty(bus.getLicensePlate(), "licensePlate", errors);
			checkIfEmpty(bus.getPrimaryDriverId(), "primaryDriverId", errors);
		}else if (bus.getMode().equals(Mode.EDIT)){
			checkIfEmpty(bus.getId(), "id", errors);
			checkIfEmpty(bus.getLicensePlate(), "licensePlate", errors);
			checkIfEmpty(bus.getPrimaryDriverId(), "primaryDriverId", errors);	
		}else if (bus.getMode().equals(Mode.DELETE)){
			checkIfEmpty(bus.getId(), "id", errors);	
		}
		
	}

}
