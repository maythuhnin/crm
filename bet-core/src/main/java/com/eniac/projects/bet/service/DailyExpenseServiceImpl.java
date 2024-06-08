package com.eniac.projects.bet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IBusDao;
import com.eniac.projects.bet.dao.interfaces.IDailyExpenseDao;
import com.eniac.projects.bet.dao.interfaces.IExpenseItemDao;
import com.eniac.projects.bet.dao.interfaces.IExpenseTypeDao;
import com.eniac.projects.bet.dao.interfaces.IInventoryDao;
import com.eniac.projects.bet.dao.interfaces.IStockDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.BusBean;
import com.eniac.projects.bet.model.DailyExpenseBean;
import com.eniac.projects.bet.model.ExpenseItemBean;
import com.eniac.projects.bet.model.InventoryBean;
import com.eniac.projects.bet.model.StockBean;

@Service
public class DailyExpenseServiceImpl {

	@Autowired
	private IDailyExpenseDao dailyExpenseDao;
	
	@Autowired
	private IExpenseItemDao expenseItemDao;
	
	@Autowired
	private IInventoryDao inventoryDao;

	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private IBusDao busDao;
	
	@Autowired
	private IExpenseTypeDao expenseTypeDao;
	
	public void createDailyExpense(DailyExpenseBean dailyExpense) throws MyBatisException, BuisnessException, DuplicatedEntryException {
		
		dailyExpenseDao.insert(dailyExpense);
		
		if(dailyExpense.getExpenseItemList().size() > 0) {
			
			BusBean bus = busDao.selectById(dailyExpense.getBusId());
			for(ExpenseItemBean item : dailyExpense.getExpenseItemList()) {
				if(item.getExpenseTypeId() != null) {
					item.setExpenseType(expenseTypeDao.selectById(item.getExpenseTypeId()).getName());
				}
				item.setDailyExpenseId(dailyExpense.getId());
				expenseItemDao.insert(item);
				if(item.getInventoryId() != null) {
					InventoryBean inventory = inventoryDao.selectById(item.getInventoryId());
					inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
					inventoryDao.update(inventory);
					
					stockDao.insert(new StockBean(item.getInventoryId(), item.getQuantity(), false, dailyExpense.getToDate(), bus.getLicensePlate()));
				}
			}
		}
	}
	
	public void updateDailyExpense(DailyExpenseBean dailyExpense) throws MyBatisException, BuisnessException {
		
		
	}

	public List<Object> selectForDatatable(Map<String,Object> criteria) throws MyBatisException {
		return dailyExpenseDao.selectForDatatable(criteria);
	}
	
	public List<Object> selectForIncomeDatatable(Map<String,Object> criteria) throws MyBatisException {
		return dailyExpenseDao.selectForIncomeDatatable(criteria);
	}
	
	public List<Object> selectForSubDatatable(int dailyExpenseId) throws MyBatisException {
		return expenseItemDao.selectForDatatable(dailyExpenseId);
	}

	public void deleteDailyExpense(int dailyExpenseId) throws MyBatisException {
		dailyExpenseDao.delete(dailyExpenseId);
	}
}
