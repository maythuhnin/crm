package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IPathExpenseDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathExpenseBean;

@Service
public class PathExpenseServiceImpl {

	@Autowired
	private IPathExpenseDao pathExpenseDao;

	public void createPathExpense(PathExpenseBean pathExpense) throws MyBatisException, BuisnessException {
		pathExpenseDao.insert(pathExpense);
	}

	public List<Object> selectForDatatable(int id) throws MyBatisException {
		return pathExpenseDao.selectForDatatable(id);
	}

	public void deletePathExpense(int pathExpenseId) throws MyBatisException {
		pathExpenseDao.delete(pathExpenseId);
	}
}
