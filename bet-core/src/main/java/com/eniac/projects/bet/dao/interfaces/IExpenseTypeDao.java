package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.ExpenseTypeBean;

public interface IExpenseTypeDao {

	public int insert(ExpenseTypeBean expenseType) throws MyBatisException;
	
	public int update(ExpenseTypeBean expenseType) throws MyBatisException;
	
	public void delete(int expenseTypeId) throws MyBatisException;
	
	public ExpenseTypeBean selectById(int id) throws MyBatisException;
	
	public List<ExpenseTypeBean> selectForDropDown() throws MyBatisException;
	
	public List<ExpenseTypeBean> selectForDataTable() throws MyBatisException;
	
}
