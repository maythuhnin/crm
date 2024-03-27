package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.DestinationBean;

public interface IDestinationMapper {
	
	int insertDestination(DestinationBean destination);
	
	int updateDestination(DestinationBean destination);
	
	int deleteDestination(int id);
	
	List<Object> selectForDatatable();
	
	List<DestinationBean> selectForDropDown();

}
