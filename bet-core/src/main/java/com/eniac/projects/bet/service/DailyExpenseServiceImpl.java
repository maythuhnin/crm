package com.eniac.projects.bet.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.DateTimeAtCompleted;

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
	
	public DailyExpenseBean selectById(int expenseId) throws MyBatisException {
		return dailyExpenseDao.selectById(expenseId);
	}
	
	public void updateDailyExpense(DailyExpenseBean dailyExpense) throws MyBatisException, BuisnessException, DuplicatedEntryException {
		
		dailyExpenseDao.update(dailyExpense);
		List<ExpenseItemBean> oldItemList = new ArrayList<ExpenseItemBean>();
		List<ExpenseItemBean> oldInsertedItemList = expenseItemDao.selectByExpenseId(dailyExpense.getId()); 
		
		if(dailyExpense.getExpenseItemList().size() > 0) {
		
			
			BusBean bus = busDao.selectById(dailyExpense.getBusId());
			
			for(ExpenseItemBean item : dailyExpense.getExpenseItemList()) {
				System.err.println(item);
				//add new items
				if(item.getId() == 0) {
					if(item.getExpenseTypeId() != null) {
						item.setExpenseType(expenseTypeDao.selectById(item.getExpenseTypeId()).getName());
					}
					item.setDailyExpenseId(dailyExpense.getId());
					System.err.println("newiTem : " + item);
					expenseItemDao.insert(item);
					if(item.getInventoryId() != null) {
						InventoryBean inventory = inventoryDao.selectById(item.getInventoryId());
						inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
						inventoryDao.update(inventory);
						
						stockDao.insert(new StockBean(item.getInventoryId(), item.getQuantity(), false, dailyExpense.getToDate(), bus.getLicensePlate()));
					}
				}else {
					oldItemList.add(item);
				}
				
				
			}
			
			List<ExpenseItemBean> deleteList = new ArrayList<ExpenseItemBean>();
			deleteList.addAll(oldInsertedItemList);
			
			for(ExpenseItemBean oldItem : oldInsertedItemList) {
				for(ExpenseItemBean updItem : oldItemList) {
					if(oldItem.getId() == updItem.getId()) {
						deleteList.remove(oldItem);
					}
				}
			}
			
			if(deleteList.size() > 0) {
				for(ExpenseItemBean deleteItem : deleteList) {
					
					//add back if inventory 
					if(null != deleteItem.getInventoryId() && deleteItem.getInventoryId() > 0) {
						InventoryBean inventory = inventoryDao.selectById(deleteItem.getInventoryId());
						inventory.setQuantity(inventory.getQuantity() + deleteItem.getQuantity());
						inventoryDao.update(inventory);
						
						stockDao.insert(new StockBean(deleteItem.getInventoryId(), deleteItem.getQuantity(), true, new Date(System.currentTimeMillis()), "DELETED Daily Income/Expense for : " + busDao.selectById(dailyExpense.getBusId()).getLicensePlate() + " ["+ dailyExpense.getFromDateAsString() + " ~ " + dailyExpense.getToDateAsString() + "]"));
					}
					
					expenseItemDao.delete(deleteItem.getId());
				}
			}
			
		}else {
			List<ExpenseItemBean> itemList = expenseItemDao.selectByExpenseId(dailyExpense.getId()); 
			for(ExpenseItemBean item : itemList) {
				if(null != item.getInventoryId() && item.getInventoryId() > 0) {
					InventoryBean inventory = inventoryDao.selectById(item.getInventoryId());
					inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
					inventoryDao.update(inventory);
					
					stockDao.insert(new StockBean(item.getInventoryId(), item.getQuantity(), true, new Date(System.currentTimeMillis()), "DELETED Daily Income/Expense for : " + busDao.selectById(dailyExpense.getBusId()).getLicensePlate() + " ["+ dailyExpense.getFromDateAsString() + " ~ " + dailyExpense.getToDateAsString() + "]"));
				}
			}
			
			expenseItemDao.delete(dailyExpense.getId());
		}
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

	public void deleteDailyExpense(int dailyExpenseId) throws MyBatisException, DuplicatedEntryException {
		
		DailyExpenseBean dailyExpense = dailyExpenseDao.selectById(dailyExpenseId);
		List<ExpenseItemBean> itemList = expenseItemDao.selectByExpenseId(dailyExpenseId);

		for(ExpenseItemBean item : itemList) {
			if(null != item.getInventoryId() && item.getInventoryId() > 0) {
				InventoryBean inventory = inventoryDao.selectById(item.getInventoryId());
				inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
				inventoryDao.update(inventory);
				
				stockDao.insert(new StockBean(item.getInventoryId(), item.getQuantity(), true, new Date(System.currentTimeMillis()), "DELETED Daily Income/Expense for : " + busDao.selectById(dailyExpense.getBusId()).getLicensePlate() + " ["+ dailyExpense.getFromDateAsString() + " ~ " + dailyExpense.getToDateAsString() + "]"));
			}
		}
		
		int deleted = expenseItemDao.delete(dailyExpenseId);
		if(deleted > 0) {
			dailyExpenseDao.delete(dailyExpenseId);
		}
		
	}
}
