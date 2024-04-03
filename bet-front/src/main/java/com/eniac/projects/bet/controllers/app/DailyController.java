package com.eniac.projects.bet.controllers.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;

@Controller
@RequestMapping("/daily")
public class DailyController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String daily(Model model) {
		model.addAttribute("title", "Daily Income/Expense");
		model.addAttribute("url", "/daily");
		model.addAttribute("page", "daily");
		return "daily";
	}

}
