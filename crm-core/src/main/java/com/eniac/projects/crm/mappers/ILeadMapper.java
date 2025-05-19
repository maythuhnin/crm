package com.eniac.projects.crm.mappers;

import java.util.List;

import com.eniac.projects.crm.model.LeadBean;

public interface ILeadMapper {
	
	int insert(LeadBean lead);
	
	int update(LeadBean lead);
	
	List<Object> selectForDatatable();

	LeadBean selectById(int id);

}
