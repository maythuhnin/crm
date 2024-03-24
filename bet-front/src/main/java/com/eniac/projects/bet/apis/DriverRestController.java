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

import com.eniac.projects.bet.apis.validators.DriverApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DriverBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.DriverServiceImpl;

@RestController
public class DriverRestController extends BaseController {

	@Autowired
	DriverServiceImpl driverService;

	@Autowired
	DriverApiValidator driverApiValidator;

	@GetMapping("/driver/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForDriversDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", driverService.selectForDatatable());
		return results;
	}
	
	@GetMapping("/driver/api/dropdown")
	@ResponseStatus(HttpStatus.OK)
	public List<DriverBean> getForDriversDropDown() throws MyBatisException {
		
		return driverService.selectForDropDown();
	}

	@PostMapping("/driver/api/add")
	public Map<String, Object> addDriver(@RequestBody DriverBean driver, HttpServletResponse response) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			driver.setMode(Mode.ADD);
			
			BindException bindException = new BindException(driver, "driver");
			
			ValidationUtils.invokeValidator(driverApiValidator, driver, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("driver", driver);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			driver.setUpdatedId(getLoggedInUser().getId());
			int createdId = driverService.createDriver(driver);
			response.setStatus(200);
			results.put("createdId", createdId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/driver/api/edit")
	public Map<String, Object> editDriver(@RequestBody DriverBean driver, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			driver.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(driver, "driver");
			
			ValidationUtils.invokeValidator(driverApiValidator, driver, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("driver", driver);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			
				driverService.updateDriver(driver);
				results.put("httpStatus", HttpStatus.OK);
				results.put("status", "Success!");
			

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("driver", driver);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/driver/api/delete")
	public Map<String, Object> deleteDriver(@RequestBody Integer driverId, HttpServletResponse response) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			DriverBean driver = new DriverBean();
			driver.setMode(Mode.DELETE);
			driver.setId(driverId);
			
			BindException bindException = new BindException(driver, "driver");
			
			ValidationUtils.invokeValidator(driverApiValidator, driver, bindException);
			
			if (bindException.hasErrors()) {
				
				response.sendError(400);
				results.put("driver", driver);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			driverService.deleteDriver(driverId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("driverId", driverId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("driverId", driverId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
