package com.eniac.projects.bet.apis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.service.DailyExpenseServiceImpl;

@RestController
public class IncomeReportRestController extends BaseController {

	@Autowired
	DailyExpenseServiceImpl dailyExpenseService;

	@PostMapping("/income-report/api/datatable")
	public Map<String, Object> getForDailyExpensesDatatable(@RequestParam String month) throws MyBatisException {
		
		Map<String, Object> results = new HashMap<String, Object>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		
		String[] dateArr = month.split("/");
		criteria.put("month", dateArr[0]);
		criteria.put("year", dateArr[1]);
		
		results.put("responseData", dailyExpenseService.selectForIncomeDatatable(criteria));
		
		return results;
	}

}
