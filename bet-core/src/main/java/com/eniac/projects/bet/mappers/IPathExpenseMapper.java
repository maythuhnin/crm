package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.PathExpenseBean;

public interface IPathExpenseMapper {
	
	int insertPathExpense(PathExpenseBean pathExpense);
	
	int deletePathExpense(int pathId);
	
	List<Object> selectForDatatable(int id);

}
