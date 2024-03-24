package com.eniac.projects.bet.dao.interfaces;

import java.util.List;

import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DriverBean;

public interface IDriverDao {

	public int insert(DriverBean driver) throws MyBatisException;
	
	public void update(DriverBean driver) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int driverId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public List<DriverBean> selectForDropDown() throws MyBatisException;
	
	public DriverBean selectById(int id) throws MyBatisException;
	
}
