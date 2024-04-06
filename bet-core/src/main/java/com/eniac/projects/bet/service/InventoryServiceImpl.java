package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IInventoryDao;
import com.eniac.projects.bet.dao.interfaces.IStockDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.InventoryBean;
import com.eniac.projects.bet.model.StockBean;

@Service
public class InventoryServiceImpl {

	@Autowired
	private IInventoryDao inventoryDao;
	
	@Autowired
	private IStockDao stockDao;

	public void createInventory(InventoryBean inventory) throws MyBatisException, BuisnessException {

		int inventoryId = inventoryDao.insert(inventory);
		
		stockDao.insert(new StockBean(inventoryId, inventory.getQuantity(), true, inventory.getReceivedDate()));

	}

	public void updateInventory(InventoryBean inventory) throws MyBatisException, BuisnessException {

		try {

			inventoryDao.update(inventory);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating Inventory : " + inventory, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return inventoryDao.selectForDatatable();
	}
	
	public List<InventoryBean> selectForDropDown() throws MyBatisException {
		return inventoryDao.selectForDropDown();
	}

	public void deleteInventory(int inventoryId) throws MyBatisException {
		if(inventoryId != 0) {
			stockDao.delete(inventoryId);
			inventoryDao.delete(inventoryId);
		}
	}
}
