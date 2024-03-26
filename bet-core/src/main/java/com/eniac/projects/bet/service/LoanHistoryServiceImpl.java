package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.ILoanHistoryDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.LoanHistoryBean;

@Service
public class LoanHistoryServiceImpl {

	@Autowired
	private ILoanHistoryDao loanHistoryDao;

	public int createLoanHistory(LoanHistoryBean loanHistory) throws MyBatisException, BuisnessException {

		return loanHistoryDao.insert(loanHistory);

	}

	public void updateLoanHistory(LoanHistoryBean loanHistory) throws MyBatisException, BuisnessException {

		try {

			loanHistoryDao.update(loanHistory);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating LoanHistory : " + loanHistory, e);
		}
	}

	public List<Object> selectForDatatable(Integer driverId) throws MyBatisException {
		return loanHistoryDao.selectForDatatable(driverId);
	}
	


	public void deleteLoanHistory(int loanHistoryId) throws MyBatisException {
		loanHistoryDao.delete(loanHistoryId);
	}
}
