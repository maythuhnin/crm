package com.eniac.projects.crm.controllers.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.crm.exception.MyBatisException;

@Controller
@RequestMapping("/credits")
public class CreditsController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String credits(Model model) throws MyBatisException {
		model.addAttribute("page", "credits");

	
		return "credits";
	}

}
