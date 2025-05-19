package com.eniac.projects.crm.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.crm.controllers.base.BaseController;

@Controller
@RequestMapping("/sales")
public class SalesController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String dashboard(Model model) {
		model.addAttribute("page", "sales");
		model.addAttribute("title", "Sales");
		model.addAttribute("titleDescription", "Add description of screen.");

		return "sales";
	}

}
