package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.apis.validators.DestinationApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DestinationBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.DestinationServiceImpl;

@RestController
public class DestinationRestController extends BaseController {

	@Autowired
	DestinationServiceImpl destinationService;

	@Autowired
	DestinationApiValidator destinationApiValidator;

	@GetMapping("/destination/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForDestinationsDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", destinationService.selectForDatatable());
		return results;
	}
	
	@GetMapping("/destination/api/dropdown")
	@ResponseStatus(HttpStatus.OK)
	public List<DestinationBean> getForDestinationsDropDown() throws MyBatisException {
		
		return destinationService.selectForDropDown();
	}

	@PostMapping("/destination/api/add")
	public Map<String, Object> addDestination(@RequestBody DestinationBean destination, HttpServletResponse response) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			destination.setMode(Mode.ADD);
			
			BindException bindException = new BindException(destination, "destination");
			
			ValidationUtils.invokeValidator(destinationApiValidator, destination, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("destination", destination);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			destination.setUpdatedId(getLoggedInUser().getId());
			int createdId = destinationService.createDestination(destination);
			response.setStatus(200);
			results.put("createdId", createdId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/destination/api/edit")
	public Map<String, Object> editDestination(@RequestBody DestinationBean destination, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			destination.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(destination, "destination");
			
			ValidationUtils.invokeValidator(destinationApiValidator, destination, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("destination", destination);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			
				destinationService.updateDestination(destination);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("destination", destination);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/destination/api/delete")
	public Map<String, Object> deleteDestination(@RequestBody Integer destinationId, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			DestinationBean destination = new DestinationBean();
			destination.setMode(Mode.DELETE);
			destination.setId(destinationId);
			
			BindException bindException = new BindException(destination, "destination");
			
			ValidationUtils.invokeValidator(destinationApiValidator, destination, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("destination", destination);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			destinationService.deleteDestination(destinationId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("destinationId", destinationId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("destinationId", destinationId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
