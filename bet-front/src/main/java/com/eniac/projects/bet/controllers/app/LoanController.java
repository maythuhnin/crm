package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/loan")
public class LoanController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("title", "Loan");
		model.addAttribute("titleDescription", "ADD | EDIT | DELETE user accounts.");
		model.addAttribute("url", "/loan");
		model.addAttribute("page", "loan");
		return "loan";
	}

}
