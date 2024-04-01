package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.ExpenseTypeBean;

public interface IExpenseTypeMapper {
	
	int insertExpenseType(ExpenseTypeBean expenseType);
	
	int deleteExpenseType(int id);
	
	List<ExpenseTypeBean> selectForDropDown();

}
