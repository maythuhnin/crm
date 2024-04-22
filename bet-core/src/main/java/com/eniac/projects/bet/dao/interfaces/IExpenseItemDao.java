package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseItemBean;

public interface IExpenseItemDao {

	public void insert(ExpenseItemBean expenseItem) throws MyBatisException;
	
	public void delete(int dailyExpenseId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
		
}
