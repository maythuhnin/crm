package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/bus")
public class BusController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("title", "Bus");
		model.addAttribute("titleDescription", "ADD | EDIT | DELETE user accounts.");
		model.addAttribute("url", "/bus");
		model.addAttribute("page", "bus");
		return "bus";
	}

}
