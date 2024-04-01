package com.eniac.projects.bet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IExpenseTypeDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseTypeBean;

@Service
public class ExpenseTypeServiceImpl {

	@Autowired
	private IExpenseTypeDao expenseTypeDao;

	public int createExpenseType(ExpenseTypeBean expenseType) throws MyBatisException, BuisnessException {
		return expenseTypeDao.insert(expenseType);
	}

	public List<ExpenseTypeBean> selectForDropDown() throws MyBatisException {
		return expenseTypeDao.selectForDropDown();
	}

	public void deleteExpenseType(int expenseTypeId) throws MyBatisException {
		expenseTypeDao.delete(expenseTypeId);
	}
}
