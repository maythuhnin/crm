package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathExpenseBean;

public interface IPathExpenseDao {

	public void insert(PathExpenseBean pathExpense) throws MyBatisException;
	
	public void delete(int pathId) throws MyBatisException;
	
	public List<Object> selectForDatatable(int id) throws MyBatisException;
	
}
