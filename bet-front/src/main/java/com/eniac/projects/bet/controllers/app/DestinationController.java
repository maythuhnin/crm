package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/destination")
public class DestinationController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("title", "Destination");
		model.addAttribute("url", "/destination");
		model.addAttribute("page", "destination");
		return "destination";
	}

}
