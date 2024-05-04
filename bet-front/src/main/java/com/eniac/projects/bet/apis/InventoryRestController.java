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

import com.eniac.projects.bet.apis.validators.InventoryApiValidator;
import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.InventoryBean;
import com.eniac.projects.bet.model.BaseBean.Mode;
import com.eniac.projects.bet.service.InventoryServiceImpl;

@RestController
public class InventoryRestController extends BaseController {

	@Autowired
	InventoryServiceImpl inventoryService;

	@Autowired
	InventoryApiValidator inventoryApiValidator;

	@GetMapping("/inventory/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForInventorysDatatable() throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", inventoryService.selectForDatatable());
		return results;
	}
	
	@GetMapping("/inventory/stock/api/datatable")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getForInventorysDatatable(@RequestParam(required=false) Integer inventoryId) throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("responseData", inventoryService.selectForStockHistoryDatatable(inventoryId));
		return results;
	}
	
	@GetMapping("/inventory/api/dropdown")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryBean> getForInventoryDropDown() throws MyBatisException {
		
		return inventoryService.selectForDropDown();
	}

	@PostMapping("/inventory/api/add")
	public Map<String, Object> addInventory(@RequestBody InventoryBean inventory) {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			inventory.setMode(Mode.ADD);
			
			BindException bindException = new BindException(inventory, "inventory");
			
			ValidationUtils.invokeValidator(inventoryApiValidator, inventory, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("inventory", inventory);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}

			inventory.setUpdatedId(getLoggedInUser().getId());
			inventoryService.createInventory(inventory);
			
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;

	}

	@PostMapping("/inventory/api/edit")
	public Map<String, Object> editInventory(@RequestBody InventoryBean inventory) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			inventory.setMode(Mode.EDIT);
			
			BindException bindException = new BindException(inventory, "inventory");
			
			ValidationUtils.invokeValidator(inventoryApiValidator, inventory, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("inventory", inventory);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			inventoryService.updateInventory(inventory);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");

		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (BuisnessException e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("inventory", inventory);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

	@PostMapping("/inventory/api/delete")
	public Map<String, Object> deleteInventory(@RequestBody Integer inventoryId) throws MyBatisException {

		Map<String, Object> results = new HashMap<String, Object>();

		try {
			
			InventoryBean inventory = new InventoryBean();
			inventory.setMode(Mode.DELETE);
			inventory.setId(inventoryId);
			
			BindException bindException = new BindException(inventory, "inventory");
			
			ValidationUtils.invokeValidator(inventoryApiValidator, inventory, bindException);
			
			if (bindException.hasErrors()) {
				
				results.put("inventory", inventory);
				results.put("httpStatus", HttpStatus.BAD_REQUEST);
				results.put("error", "Validation Error.");
				
				return results;
			}
			
			inventoryService.deleteInventory(inventoryId);
			results.put("httpStatus", HttpStatus.OK);
			results.put("status", "Success!");
		} catch (MyBatisException e) {
			e.printStackTrace();
			results.put("inventoryId", inventoryId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", MYBATIS_EXCEPTION_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			results.put("inventoryId", inventoryId);
			results.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			results.put("error", BUSINESS_EXCEPTION_MSG);
		}

		return results;
	}

}
