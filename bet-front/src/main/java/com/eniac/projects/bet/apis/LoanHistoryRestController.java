package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.LoanHistoryApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.LoanHistoryBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.LoanHistoryServiceImpl;

@RestController
public class LoanHistoryRestController extends BaseController {

	@Autowired
	LoanHistoryServiceImpl loanHistoryService;

	@Autowired
	LoanHistoryApiValidator loanHistoryApiValidator;

	@GetMapping("/loan/api/{id}/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForLoansDatatable(@PathVariable(value="id") Integer id) throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", loanHistoryService.selectForDatatable(id));
		return results;
	}
	
	@PostMapping("/loan/api/add")
	public Map<String, Object> addLoan(@RequestBody LoanHistoryBean loan) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			loan.setMode(Mode.ADD);
			
			BindException bindException = new BindException(loan, "loan");
			
			ValidationUtils.invokeValidator(loanHistoryApiValidator, loan, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("loan", loan);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			loan.setUpdatedId(getLoggedInUser().getId());
			int createdId = loanHistoryService.createLoanHistory(loan);
			results.put("createdId", createdId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/loan/api/edit")
	public Map<String, Object> editLoan(@RequestBody LoanHistoryBean loan) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			loan.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(loan, "loan");
			
			ValidationUtils.invokeValidator(loanHistoryApiValidator, loan, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("loan", loan);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			loanHistoryService.updateLoanHistory(loan);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("loan", loan);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/loan/api/delete")
	public Map<String, Object> deleteLoan(@RequestBody Integer loanId) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			LoanHistoryBean loan = new LoanHistoryBean();
			loan.setMode(Mode.DELETE);
			loan.setId(loanId);
			
			BindException bindException = new BindException(loan, "loan");
			
			ValidationUtils.invokeValidator(loanHistoryApiValidator, loan, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("loan", loan);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			loanHistoryService.deleteLoanHistory(loanId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("loanId", loanId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("loanId", loanId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
