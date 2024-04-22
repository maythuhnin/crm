package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.DailyExpenseApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DailyExpenseBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.DailyExpenseServiceImpl;

@RestController
public class DailyExpenseRestController extends BaseController {

	@Autowired
	DailyExpenseServiceImpl dailyExpenseService;

	@Autowired
	DailyExpenseApiValidator dailyExpenseApiValidator;
	
	@GetMapping("/daily-expense/api/datatable")
	public Map<String, Object> getForDailyExpensesDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", dailyExpenseService.selectForDatatable());
		return results;
	}

	@PostMapping("/daily-expense/api/add")
	public Map<String, Object> addDailyExpense(@RequestBody DailyExpenseBean dailyExpense) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			dailyExpense.setMode(Mode.ADD);
			
			BindException bindException = new BindException(dailyExpense, "dailyExpense");
			
			ValidationUtils.invokeValidator(dailyExpenseApiValidator, dailyExpense, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("dailyExpense", dailyExpense);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			System.err.println(dailyExpense);
			dailyExpense.setUpdatedId(getLoggedInUser().getId());
			dailyExpenseService.createDailyExpense(dailyExpense);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/daily-expense/api/edit")
	public Map<String, Object> editDailyExpense(@RequestBody DailyExpenseBean dailyExpense) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			dailyExpense.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(dailyExpense, "dailyExpense");
			
			ValidationUtils.invokeValidator(dailyExpenseApiValidator, dailyExpense, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("dailyExpense", dailyExpense);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			dailyExpense.setUpdatedId(getLoggedInUser().getId());
			dailyExpenseService.updateDailyExpense(dailyExpense);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("dailyExpense", dailyExpense);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/daily-expense/api/delete")
	public Map<String, Object> deleteDailyExpense(@RequestBody Integer dailyExpenseId) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			DailyExpenseBean dailyExpense = new DailyExpenseBean();
			dailyExpense.setMode(Mode.DELETE);
			dailyExpense.setId(dailyExpenseId);
			
			BindException bindException = new BindException(dailyExpense, "dailyExpense");
			
			ValidationUtils.invokeValidator(dailyExpenseApiValidator, dailyExpense, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("dailyExpense", dailyExpense);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			dailyExpenseService.deleteDailyExpense(dailyExpenseId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("dailyExpenseId", dailyExpenseId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("dailyExpenseId", dailyExpenseId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
