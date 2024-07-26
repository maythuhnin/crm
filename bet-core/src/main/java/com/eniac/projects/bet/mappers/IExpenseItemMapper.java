package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.ExpenseItemBean;

public interface IExpenseItemMapper {
	
	int insertExpenseItem(ExpenseItemBean expenseItem);
	
	int delete(int id);
	
	int deleteExpenseItem(int dailyExpenseId);
	
	ExpenseItemBean selectById(int itemId);
	
	List<ExpenseItemBean> selectByExpenseId(int dailyExpenseId);
	
	List<Object> selectForDatatable(int dailyExpenseId);

}
