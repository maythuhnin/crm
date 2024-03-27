package com.eniac.projects.bet.dao.interfaces;

import java.util.List;

import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DestinationBean;

public interface IDestinationDao {

	public int insert(DestinationBean destination) throws MyBatisException;
	
	public void update(DestinationBean destination) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int destinationId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public List<DestinationBean> selectForDropDown() throws MyBatisException;
		
}
