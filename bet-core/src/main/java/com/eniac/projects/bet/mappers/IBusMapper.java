package com.eniac.projects.bet.mappers;

import java.util.List;
import java.util.Map;

import com.eniac.projects.bet.model.BusBean;

public interface IBusMapper {
	
	int insertBus(BusBean bus);
	
	int updateBus(BusBean bus);
	
	int updateLoggedIn(int id);
	
	int deleteBus(int id);
	
	int removePrimaryDriver(int id);
	
	int removeSecondaryDriver(int id);
	
	List<Object> selectForDatatable();

	BusBean selectByCriteria(Map<String, Object> criteria);

	BusBean selectById(int id);
	
	List<BusBean> selectForDropDown();

}
