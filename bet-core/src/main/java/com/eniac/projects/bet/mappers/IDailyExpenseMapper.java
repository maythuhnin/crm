package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.DailyExpenseBean;

public interface IDailyExpenseMapper {
	
	int insertDailyExpense(DailyExpenseBean dailyExpense);
	
	int updateDailyExpense(DailyExpenseBean dailyExpense);
	
	int deleteDailyExpense(int id);
	
	List<Object> selectForDatatable();
	
	DailyExpenseBean selectById(int id);

}
