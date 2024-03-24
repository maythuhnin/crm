package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.DriverBean;

public interface IDriverMapper {
	
	int insertDriver(DriverBean driver);
	
	int updateDriver(DriverBean driver);
	
	int deleteDriver(int id);
	
	List<Object> selectForDatatable();
	
	List<DriverBean> selectForDropDown();

	DriverBean selectById(int id);

}
