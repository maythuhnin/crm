package com.eniac.projects.crm.apis.validators;

import java.sql.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CommonValidator {

	private static String REQUIRED_MSG = "This field is required.";
	
	protected void checkIfEmpty(int value, String fieldName, Errors errors) {
		
		if(value == 0) {
			errors.rejectValue(fieldName, REQUIRED_MSG);
		}
		
	}
	
	protected void checkIfEmpty(Double value, String fieldName, Errors errors) {
		
		if(value == 0) {
			errors.rejectValue(fieldName, REQUIRED_MSG);
		}
		
	}
	
	protected void checkIfEmpty(Integer value, String fieldName, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, fieldName, REQUIRED_MSG);
	}
	
	protected void checkIfEmpty(String value, String fieldName, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, fieldName, REQUIRED_MSG);
		
	}
	
	protected void checkIfEmpty(Date value, String fieldName, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, fieldName, REQUIRED_MSG);
		
	}
}
