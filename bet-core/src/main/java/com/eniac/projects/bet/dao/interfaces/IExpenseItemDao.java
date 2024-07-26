package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseItemBean;

public interface IExpenseItemDao {

	public void insert(ExpenseItemBean expenseItem) throws MyBatisException;
	
	public int delete(int id) throws MyBatisException;
	
	public int deleteByExpenseId(int dailyExpenseId) throws MyBatisException;
	
	public ExpenseItemBean selectById(int id) throws MyBatisException;
	
	public List<ExpenseItemBean> selectByExpenseId(int dailyExpenseId) throws MyBatisException;
	
	public List<Object> selectForDatatable(int dailyExpenseId) throws MyBatisException;
		
}
