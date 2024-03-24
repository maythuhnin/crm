package com.eniac.projects.bet.controllers.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eniac.projects.bet.exception.MyBatisException;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String login(@RequestParam(value= "errorType", required=false) String errorType, Model model) throws MyBatisException {
		return "login";
	}

}
