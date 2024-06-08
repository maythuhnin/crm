package com.eniac.projects.bet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IPathBusDao;
import com.eniac.projects.bet.dao.interfaces.IPathDao;
import com.eniac.projects.bet.dao.interfaces.IPathExpenseDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathBean;
import com.eniac.projects.bet.model.PathBusBean;
import com.eniac.projects.bet.model.PathExpenseBean;

@Service
public class PathServiceImpl {

	@Autowired
	private IPathDao pathDao;
	
	@Autowired
	private IPathExpenseDao pathExpenseDao;

	@Autowired
	private IPathBusDao pathBusDao;
	
	public void createPath(PathBean path) throws MyBatisException, BuisnessException {

		if(null != path.getPathExpenseList() && path.getPathExpenseList().size() > 0) {
			int pathId = pathDao.insert(path);
			
			for(PathExpenseBean expense: path.getPathExpenseList()) {
				expense.setPathId(pathId);
				pathExpenseDao.insert(expense);
			}
			
			if(null != path.getPathBusList() && path.getPathBusList().size() > 0) {
				for(PathBusBean bus: path.getPathBusList()) {
					bus.setPathId(pathId);
					pathBusDao.insert(bus);
				}
			}
		}
		

	}

	public void updatePath(PathBean path) throws MyBatisException, BuisnessException {

		try {
			
			pathExpenseDao.deleteWithPathId(path.getId());
			
			if(null != path.getPathExpenseList() && path.getPathExpenseList().size() > 0) {
				for(PathExpenseBean expense: path.getPathExpenseList()) {
					expense.setPathId(path.getId());
					pathExpenseDao.insert(expense);
				}
			}
			
			pathBusDao.delete(path.getId());
			
			if(null != path.getPathBusList() && path.getPathBusList().size() > 0) {
				for(PathBusBean bus: path.getPathBusList()) {
					bus.setPathId(path.getId());
					pathBusDao.insert(bus);
				}
			}
			
			pathDao.update(path);
			
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating Path : " + path, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return pathDao.selectForDatatable();
	}
	
	public List<Object> selectForDropDown(Map<String,Object> criteria) throws MyBatisException {
		return pathDao.selectForDropDown(criteria);
	}

	public PathBean selectById(int id) throws MyBatisException {
		return pathDao.selectById(id);
	}

	public void deletePath(int pathId) throws MyBatisException {
		
		if(pathId > 0) {
			pathBusDao.delete(pathId);
			pathExpenseDao.deleteWithPathId(pathId);
			pathDao.delete(pathId);
		}
		
	}
}
