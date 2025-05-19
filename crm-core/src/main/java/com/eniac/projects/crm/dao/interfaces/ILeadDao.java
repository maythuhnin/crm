package com.eniac.projects.crm.dao.interfaces;

import java.util.List;

import com.eniac.projects.crm.exception.DuplicatedEntryException;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.LeadBean;

public interface ILeadDao {

	public void insert(LeadBean lead) throws MyBatisException;
	
	public void update(LeadBean lead) throws DuplicatedEntryException, MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public LeadBean selectById(int id) throws MyBatisException;
	
}
