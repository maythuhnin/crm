package com.eniac.projects.bet.mappers;

import java.util.List;
import java.util.Map;

import com.eniac.projects.bet.model.DailyExpenseBean;

public interface IDailyExpenseMapper {
	
	int insertDailyExpense(DailyExpenseBean dailyExpense);
	
	int updateDailyExpense(DailyExpenseBean dailyExpense);
	
	int deleteDailyExpense(int id);
	
	List<Object> selectForDatatable(Map<String,Object> criteria);
	
	DailyExpenseBean selectById(int id);

}
