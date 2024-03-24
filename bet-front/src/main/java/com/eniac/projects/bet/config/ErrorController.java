package com.eniac.projects.bet.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping(value = "errors", method = RequestMethod.GET)
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

		ModelAndView errorPage = null;
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
			case 403: {
				errorPage = new ModelAndView("error/403");
				break;
			}
			case 404: {
				errorPage = new ModelAndView("error/404");
				break;
			}
			case 405: {
				errorPage = new ModelAndView("error/405");
				break;
			}
			case 500: {
				errorPage = new ModelAndView("error/500");
				break;
			}
		}
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}
	
	@RequestMapping("/accessDenied")
	public ModelAndView accessDenied(Model model) {
		return  new ModelAndView("error/403");
	}
}
