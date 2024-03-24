package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/expense")
public class ExpenseTypeController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("title", "Expense Type");
		model.addAttribute("titleDescription", "ADD | EDIT | DELETE user accounts.");
		model.addAttribute("url", "/expense");
		model.addAttribute("page", "expense");
		return "bus";
	}

}
