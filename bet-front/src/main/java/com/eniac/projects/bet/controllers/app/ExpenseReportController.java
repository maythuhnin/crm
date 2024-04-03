package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/expense-report")
public class ExpenseReportController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String daily(Model model) {
		model.addAttribute("title", "Expense Report");
		model.addAttribute("url", "/expense-report");
		model.addAttribute("page", "expenseReport");
		return "expense-report";
	}

}
