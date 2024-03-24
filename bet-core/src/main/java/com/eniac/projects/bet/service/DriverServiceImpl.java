package com.eniac.projects.bet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IDriverDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.DriverBean;

@Service
public class DriverServiceImpl {

	@Autowired
	private IDriverDao driverDao;

	public int createDriver(DriverBean driver) throws MyBatisException, BuisnessException {

		return driverDao.insert(driver);

	}

	public void updateDriver(DriverBean driver) throws MyBatisException, BuisnessException {

		try {

			driverDao.update(driver);
		} catch (Exception e) {
			throw new BuisnessException("Driveriness Exception occured when updating Driver : " + driver, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return driverDao.selectForDatatable();
	}
	
	public List<DriverBean> selectForDropDown() throws MyBatisException {
		return driverDao.selectForDropDown();
	}

	public DriverBean selectById(int id) throws MyBatisException {
		return driverDao.selectById(id);
	}

	public void deleteDriver(int driverId) throws MyBatisException {
		driverDao.delete(driverId);
	}
}
