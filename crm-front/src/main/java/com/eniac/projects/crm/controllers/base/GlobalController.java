package com.eniac.projects.crm.controllers.base;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.UserBean;

@ControllerAdvice
public class GlobalController extends BaseController {

	@ModelAttribute
	public void loadCommonData(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			try {
				UserBean loggedInUser = getLoggedInUser();
				model.addAttribute("username", loggedInUser.getUsername());
				model.addAttribute("userRole", loggedInUser.getRole());
				model.addAttribute("name", getLoggedInUser().getName());
			} catch (MyBatisException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
