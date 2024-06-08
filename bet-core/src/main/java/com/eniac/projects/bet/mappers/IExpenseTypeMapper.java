package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.ExpenseTypeBean;

public interface IExpenseTypeMapper {
	
	int insertExpenseType(ExpenseTypeBean expenseType);

	int updateExpenseType(ExpenseTypeBean expenseType);
	
	int deleteExpenseType(int id);
	
	ExpenseTypeBean selectById(int id);
	
	List<ExpenseTypeBean> selectForDatatable();
	
	List<ExpenseTypeBean> selectForDropDown();

}
