package com.eniac.projects.bet.dao.interfaces;

import java.util.List;

import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.StockBean;

public interface IStockDao {

	public int insert(StockBean stock) throws MyBatisException;
	
	public void delete(int inventoryId) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
}
