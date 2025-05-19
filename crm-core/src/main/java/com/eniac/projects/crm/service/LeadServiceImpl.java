package com.eniac.projects.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.crm.dao.interfaces.ILeadDao;
import com.eniac.projects.crm.exception.BuisnessException;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.model.LeadBean;

@Service
public class LeadServiceImpl {

	@Autowired
	private ILeadDao leadDao;

	public void create(LeadBean lead) throws MyBatisException, BuisnessException {

		leadDao.insert(lead);

	}

	public void update(LeadBean lead) throws MyBatisException, BuisnessException {

		try {

			leadDao.update(lead);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating Lead : " + lead, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return leadDao.selectForDatatable();
	}

	public LeadBean selectById(int id) throws MyBatisException {
		return leadDao.selectById(id);
	}

}
