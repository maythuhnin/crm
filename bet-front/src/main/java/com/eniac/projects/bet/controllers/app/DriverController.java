package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/driver")
public class DriverController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String loan(Model model) {
		model.addAttribute("title", "Driver");
		model.addAttribute("url", "/driver");
		model.addAttribute("page", "driver");
		return "driver";
	}

}
