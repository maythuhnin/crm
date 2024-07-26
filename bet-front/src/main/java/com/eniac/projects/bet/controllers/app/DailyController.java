package com.eniac.projects.bet.controllers.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eniac.projects.bet.controllers.base.BaseController;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DailyExpenseBean;
import com.eniac.projects.bet.service.DailyExpenseServiceImpl;

@Controller
@RequestMapping("/daily")
public class DailyController extends BaseController {

	@Autowired
	DailyExpenseServiceImpl expenseService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String daily(Model model) {
		model.addAttribute("title", "Daily Income/Expense");
		model.addAttribute("url", "/daily");
		model.addAttribute("page", "daily");
		return "daily";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}/edit")
	public String editDaily(@PathVariable int id, Model model) {

		System.err.println("ID : " + id);
		
		try {
			DailyExpenseBean expenseBean = expenseService.selectById(id);
			model.addAttribute("expenseBean", expenseBean);
		} catch (MyBatisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("title", "Edit Daily Income/Expense");
		model.addAttribute("page", "daily");
		return "daily-edit";
	}

}
