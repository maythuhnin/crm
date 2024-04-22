package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IDailyExpenseDao;
import com.eniac.projects.bet.dao.interfaces.IExpenseItemDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DailyExpenseBean;
import com.eniac.projects.bet.model.ExpenseItemBean;

@Service
public class DailyExpenseServiceImpl {

	@Autowired
	private IDailyExpenseDao dailyExpenseDao;
	
	@Autowired
	private IExpenseItemDao expenseItemDao;

	public void createDailyExpense(DailyExpenseBean dailyExpense) throws MyBatisException, BuisnessException {
		
		dailyExpenseDao.insert(dailyExpense);
		
		if(dailyExpense.getExpenseItemList().size() > 0) {
			for(ExpenseItemBean item : dailyExpense.getExpenseItemList()) {
				item.setDailyExpenseId(dailyExpense.getId());
				expenseItemDao.insert(item);
			}
		}
	}
	
	public void updateDailyExpense(DailyExpenseBean dailyExpense) throws MyBatisException, BuisnessException {
		
		
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return dailyExpenseDao.selectForDatatable();
	}

	public void deleteDailyExpense(int dailyExpenseId) throws MyBatisException {
		dailyExpenseDao.delete(dailyExpenseId);
	}
}
