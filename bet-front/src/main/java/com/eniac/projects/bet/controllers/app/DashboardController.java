package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String dashboard(Model model) {
		model.addAttribute("page", "dashboard");
		model.addAttribute("title", "Dashboard");
		model.addAttribute("titleDescription", "Add description of screen.");

		return "dashboard";
	}

}
