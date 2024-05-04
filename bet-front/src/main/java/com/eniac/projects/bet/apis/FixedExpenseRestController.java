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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.PathApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.PathExpenseServiceImpl;
import com.eniac.projects.bet.service.PathServiceImpl;

@RestController
public class FixedExpenseRestController extends BaseController {

	@Autowired
	PathServiceImpl pathService;
	
	@Autowired
	PathExpenseServiceImpl pathExpenseService;

	@Autowired
	PathApiValidator pathApiValidator;

	@PostMapping("/fixed-expense/amount/api/datatable")
	public List<Object> getForSubTable(@RequestParam int pathId) throws MyBatisException {
		
		return pathExpenseService.selectForDatatable(pathId);
	}
	
	@GetMapping("/fixed-expense/api/datatable")
	public Map<String, Object> getForPathsDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", pathService.selectForDatatable());
		return results;
	}
	
	@PostMapping("/fixed-expense/api/dropdown")
	public List<Object> getForPathDropDown(@RequestParam String path, @RequestParam(required=false) Integer busId) throws MyBatisException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("path", path);
		criteria.put("busId", busId);
		return pathService.selectForDropDown(criteria);
	}

	@PostMapping("/fixed-expense/api/add")
	public Map<String, Object> addPath(@RequestBody PathBean path) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			path.setMode(Mode.ADD);
			
			BindException bindException = new BindException(path, "path");
			
			ValidationUtils.invokeValidator(pathApiValidator, path, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("path", path);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			path.setUpdatedId(getLoggedInUser().getId());
			pathService.createPath(path);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/fixed-expense/api/edit")
	public Map<String, Object> editPath(@RequestBody PathBean path) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			path.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(path, "path");
			
			ValidationUtils.invokeValidator(pathApiValidator, path, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("path", path);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			path.setUpdatedId(getLoggedInUser().getId());
			pathService.updatePath(path);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("path", path);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/fixed-expense/api/delete")
	public Map<String, Object> deletePath(@RequestBody Integer pathId) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			PathBean path = new PathBean();
			path.setMode(Mode.DELETE);
			path.setId(pathId);
			
			BindException bindException = new BindException(path, "path");
			
			ValidationUtils.invokeValidator(pathApiValidator, path, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("path", path);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			pathService.deletePath(pathId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("pathId", pathId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("pathId", pathId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
