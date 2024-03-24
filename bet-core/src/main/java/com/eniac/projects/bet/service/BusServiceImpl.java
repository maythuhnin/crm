package com.eniac.projects.bet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IBusDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.BusBean;

@Service
public class BusServiceImpl {

	@Autowired
	private IBusDao busDao;

	public void createBus(BusBean bus) throws MyBatisException, BuisnessException {

		busDao.insert(bus);

	}

	public void updateBus(BusBean bus) throws MyBatisException, BuisnessException {

		try {

			busDao.update(bus);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating Bus : " + bus, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return busDao.selectForDatatable();
	}

	public BusBean selectById(int id) throws MyBatisException {
		return busDao.selectById(id);
	}

	public BusBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException {
		return busDao.selectByCriteria(criteria);
	}

	public void deleteBus(int busId) throws MyBatisException {
		busDao.delete(busId);
	}
}
