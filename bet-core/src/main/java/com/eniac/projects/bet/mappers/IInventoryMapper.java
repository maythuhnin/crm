package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.InventoryBean;

public interface IInventoryMapper {
	
	int insertInventory(InventoryBean inventory);
	
	int updateInventory(InventoryBean inventory);
	
	int deleteInventory(int id);
	
	List<Object> selectForDatatable();
	
	List<InventoryBean> selectForDropDown();
}
