package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.LoanHistoryBean;

public interface ILoanHistoryMapper {
	
	int insertLoanHistory(LoanHistoryBean driver);
	
	int updateLoanHistory(LoanHistoryBean driver);
	
	int deleteLoanHistory(int id);
	
	List<Object> selectForDatatable(int driverId);

}
