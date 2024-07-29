package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.BusBean;

public interface IBusDao {

	public void insert(BusBean bus) throws MyBatisException;
	
	public void update(BusBean bus) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int busId) throws MyBatisException;
	
	public void removePrimaryDriver(int driverId) throws MyBatisException;
	
	public void removeSecondaryDriver(int driverId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public List<BusBean> selectForDropDown() throws MyBatisException;
	
	public BusBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException;
	
	public BusBean selectById(int id) throws MyBatisException;
	
}
