package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IDestinationDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DestinationBean;

@Service
public class DestinationServiceImpl {

	@Autowired
	private IDestinationDao destinationDao;

	public int createDestination(DestinationBean destination) throws MyBatisException, BuisnessException {

		return destinationDao.insert(destination);

	}

	public void updateDestination(DestinationBean destination) throws MyBatisException, BuisnessException {

		try {

			destinationDao.update(destination);
		} catch (Exception e) {
			throw new BuisnessException("Destinationiness Exception occured when updating Destination : " + destination, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return destinationDao.selectForDatatable();
	}
	
	public List<DestinationBean> selectForDropDown() throws MyBatisException {
		return destinationDao.selectForDropDown();
	}

	public void deleteDestination(int destinationId) throws MyBatisException {
		destinationDao.delete(destinationId);
	}
}
