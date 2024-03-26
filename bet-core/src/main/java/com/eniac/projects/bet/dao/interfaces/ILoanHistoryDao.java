package com.eniac.projects.bet.dao.interfaces;

import java.util.List;

import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.LoanHistoryBean;

public interface ILoanHistoryDao {

	public int insert(LoanHistoryBean loanHistory) throws MyBatisException;
	
	public void update(LoanHistoryBean loanHistory) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int loanHistoryId) throws MyBatisException;
	
	public List<Object> selectForDatatable(int driverId) throws MyBatisException;
		
}
