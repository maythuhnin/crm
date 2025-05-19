package com.eniac.projects.crm.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.crm.controllers.base.BaseController;
import com.eniac.projects.crm.exception.MyBatisException;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String user(Model model) throws MyBatisException {
		model.addAttribute("title", "User");
		model.addAttribute("titleDescription", "ADD | EDIT | DELETE user accounts.");
		model.addAttribute("url", "/user");
		model.addAttribute("page", "user");
		model.addAttribute("loggedInUserId", getLoggedInUser().getId());
		
		return "user";
	}

}
