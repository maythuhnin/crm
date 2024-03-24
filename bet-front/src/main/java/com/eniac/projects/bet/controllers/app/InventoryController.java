package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/inventory")
public class InventoryController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String daily(Model model) {
		model.addAttribute("title", "Inventory");
		model.addAttribute("titleDescription", "ADD | EDIT | DELETE user accounts.");
		model.addAttribute("url", "/inventory");
		model.addAttribute("page", "inventory");
		return "inventory";
	}

}
