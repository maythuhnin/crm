package com.eniac.projects.crm.apis;

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

import com.eniac.projects.crm.apis.validators.LeadApiValidator;
import com.eniac.projects.crm.controllers.base.BaseController;
import com.eniac.projects.crm.exception.BuisnessException;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.LeadBean;
import com.eniac.projects.crm.model.BaseBean.Mode;
import com.eniac.projects.crm.service.LeadServiceImpl;

@RestController
public class LeadRestController extends BaseController {

	@Autowired
	LeadServiceImpl leadService;

	@Autowired
	LeadApiValidator leadApiValidator;

	@GetMapping("/lead/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForDatatable() throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", leadService.selectForDatatable());
		return results;
	}

	@PostMapping("/lead/api/add")
	public Map<String, Object> add(@RequestBody LeadBean lead) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {

			lead.setMode(Mode.ADD);

			BindException bindException = new BindException(lead, "lead");

			ValidationUtils.invokeValidator(leadApiValidator, lead, bindException);

			if (bindException.hasErrors()) {

				results.put("lead", lead);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");

				return results;
			}

			lead.setUpdatedId(getLoggedInUser().getId());
			leadService.create(lead);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/lead/api/edit")
	public Map<String, Object> edit(@RequestBody LeadBean lead) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {

			lead.setMode(Mode.EDIT);

			BindException bindException = new BindException(lead, "lead");

			ValidationUtils.invokeValidator(leadApiValidator, lead, bindException);

			if (bindException.hasErrors()) {

				results.put("lead", lead);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");

				return results;
			}

			leadService.update(lead);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("lead", lead);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
