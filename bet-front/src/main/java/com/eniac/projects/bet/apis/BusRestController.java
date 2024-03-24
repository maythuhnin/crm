package com.eniac.projects.bet.apis;

import java.util.HashMap;
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

import com.eniac.projects.bet.apis.validators.BusApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.BusBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.BusServiceImpl;

@RestController
public class BusRestController extends BaseController {

	@Autowired
	BusServiceImpl busService;

	@Autowired
	BusApiValidator busApiValidator;

	@GetMapping("/bus/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForBussDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", busService.selectForDatatable());
		return results;
	}

	@PostMapping("/bus/api/add")
	public Map<String, Object> addBus(@RequestBody BusBean bus, HttpServletResponse response) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			bus.setMode(Mode.ADD);
			
			BindException bindException = new BindException(bus, "bus");
			
			ValidationUtils.invokeValidator(busApiValidator, bus, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("bus", bus);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			if (!checkBusLicenseUnique(bus)) {
				bus.setUpdatedId(getLoggedInUser().getId());
				busService.createBus(bus);
				response.setStatus(200);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			} else {
				response.sendError(500);
				results.put("bus", bus);
				results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
				results.put("error", "License Plate already exist. Please pick a different one.");
			}

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/bus/api/edit")
	public Map<String, Object> editBus(@RequestBody BusBean bus, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			bus.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(bus, "bus");
			
			ValidationUtils.invokeValidator(busApiValidator, bus, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("bus", bus);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			if (!checkBusLicenseUnique(bus)) {
				busService.updateBus(bus);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			} else {
				response.sendError(500);
				results.put("bus", bus);
				results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
				results.put("error", "License Plate already exist. Please pick a different one.");
			}

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("bus", bus);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/bus/api/delete")
	public Map<String, Object> deleteBus(@RequestBody Integer busId, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			BusBean bus = new BusBean();
			bus.setMode(Mode.DELETE);
			bus.setId(busId);
			
			BindException bindException = new BindException(bus, "bus");
			
			ValidationUtils.invokeValidator(busApiValidator, bus, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("bus", bus);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			busService.deleteBus(busId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("busId", busId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("busId", busId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	private boolean checkBusLicenseUnique(BusBean bus) throws MyBatisException {

		boolean isExist = false;
		Map<String, Object> criteria = new HashMap<String, Object>();

		criteria.put("licensePlate", bus.getLicensePlate());

		if (bus.getId() != 0) {
			criteria.put("notId", bus.getId());
		}

		BusBean selectedBus = busService.selectByCriteria(criteria);

		if (null != selectedBus) {
			isExist = true;
		}

		return isExist;
	}
}
