package com.eniac.projects.crm.controllers.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eniac.projects.crm.exception.MyBatisException;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "errorType", required = false) String errorType, Model model)
			throws MyBatisException {
		return "login";
	}

}
