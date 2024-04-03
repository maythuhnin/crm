package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/income-report")
public class IncomeReportController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String income(Model model) {
		model.addAttribute("title", "Income Report");
		model.addAttribute("url", "/income-report");
		model.addAttribute("page", "incomeReport");
		return "income-report";
	}

}
