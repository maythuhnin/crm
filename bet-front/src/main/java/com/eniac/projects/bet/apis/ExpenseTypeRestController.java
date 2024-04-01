package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.ExpenseTypeApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseTypeBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.ExpenseTypeServiceImpl;

@RestController
public class ExpenseTypeRestController extends BaseController {

	@Autowired
	ExpenseTypeServiceImpl expenseTypeService;

	@Autowired
	ExpenseTypeApiValidator expenseTypeApiValidator;
	
	@GetMapping("/expense-type/api/dropdown")
	@ResponseStatus(HttpStatus.OK)
	public List<ExpenseTypeBean> getForExpenseTypeDropDown() throws MyBatisException {
		
		return expenseTypeService.selectForDropDown();
	}

	@PostMapping("/expense-type/api/add")
	public Map<String, Object> addExpenseType(@RequestBody ExpenseTypeBean expenseType) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			expenseType.setMode(Mode.ADD);
			
			BindException bindException = new BindException(expenseType, "expenseType");
			
			ValidationUtils.invokeValidator(expenseTypeApiValidator, expenseType, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("expenseType", expenseType);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			expenseType.setUpdatedId(getLoggedInUser().getId());
			int createdId = expenseTypeService.createExpenseType(expenseType);
			results.put("createdId", createdId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("expenseType", expenseType);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("expenseType", expenseType);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("expenseType", expenseType);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/expense-type/api/delete")
	public Map<String, Object> deleteExpenseType(@RequestBody Integer expenseTypeId) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			ExpenseTypeBean expenseType = new ExpenseTypeBean();
			expenseType.setMode(Mode.DELETE);
			expenseType.setId(expenseTypeId);
			
			BindException bindException = new BindException(expenseType, "expenseType");
			
			ValidationUtils.invokeValidator(expenseTypeApiValidator, expenseType, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("expenseType", expenseType);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			expenseTypeService.deleteExpenseType(expenseTypeId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("expenseTypeId", expenseTypeId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("expenseTypeId", expenseTypeId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
