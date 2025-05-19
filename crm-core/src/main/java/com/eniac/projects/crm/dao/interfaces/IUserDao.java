package com.eniac.projects.crm.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.eniac.projects.crm.exception.DuplicatedEntryException;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.UserBean;

public interface IUserDao {

	public void insert(UserBean user) throws MyBatisException;
	
	public void update(UserBean user) throws DuplicatedEntryException, MyBatisException;
	
	public void updateLoggedIn(int id) throws MyBatisException, DuplicatedEntryException;
		
	public void delete(int userId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public UserBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException;
	
	public UserBean selectById(int id) throws MyBatisException;
	
}
