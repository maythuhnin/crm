package com.eniac.projects.bet.dao.interfaces;

import java.util.List;

import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.InventoryBean;

public interface IInventoryDao {

	public int insert(InventoryBean inventory) throws MyBatisException;
	
	public void update(InventoryBean inventory) throws DuplicatedEntryException, MyBatisException;
	
	public void delete(int inventoryId) throws MyBatisException;
	
	public InventoryBean selectById(int inventoryId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public List<InventoryBean> selectForDropDown() throws MyBatisException;
	
}
