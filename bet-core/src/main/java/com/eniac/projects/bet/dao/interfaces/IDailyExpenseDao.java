package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DailyExpenseBean;

public interface IDailyExpenseDao {

	public void insert(DailyExpenseBean dailyExpense) throws MyBatisException;
	
	public void update(DailyExpenseBean dailyExpense) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int dailyExpenseId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public DailyExpenseBean selectById(int id) throws MyBatisException;
	
}
