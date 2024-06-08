package com.eniac.projects.bet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IExpenseTypeDao;
import com.eniac.projects.bet.dao.interfaces.IPathExpenseDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseTypeBean;

@Service
public class ExpenseTypeServiceImpl {

	@Autowired
	private IExpenseTypeDao expenseTypeDao;
	
	@Autowired
	private IPathExpenseDao pathExpenseDao;

	public int createExpenseType(ExpenseTypeBean expenseType) throws MyBatisException, BuisnessException {
		return expenseTypeDao.insert(expenseType);
	}
	
	public int updateExpenseType(ExpenseTypeBean expenseType) throws MyBatisException, BuisnessException {
		return expenseTypeDao.update(expenseType);
	}

	public List<ExpenseTypeBean> selectForDropDown() throws MyBatisException {
		return expenseTypeDao.selectForDropDown();
	}
	
	public List<ExpenseTypeBean> selectForDataTable() throws MyBatisException {
		return expenseTypeDao.selectForDataTable();
	}

	public void deleteExpenseType(int expenseTypeId) throws MyBatisException {
		
		pathExpenseDao.deleteWithExpenseId(expenseTypeId);
		expenseTypeDao.delete(expenseTypeId);
	}
}
