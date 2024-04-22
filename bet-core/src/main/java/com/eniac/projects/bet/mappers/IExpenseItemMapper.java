package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.ExpenseItemBean;

public interface IExpenseItemMapper {
	
	int insertExpenseItem(ExpenseItemBean expenseItem);
	
	int deleteExpenseItem(int dailyExpenseId);
	
	List<Object> selectForDatatable();

}
