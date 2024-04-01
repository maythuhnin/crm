package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/fixed-expense")
public class FixedExpenseController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("title", "Fixed Expense");
		model.addAttribute("url", "/fixed-expense");
		model.addAttribute("page", "fixedExpense");
		return "fixed-expense";
	}

}
