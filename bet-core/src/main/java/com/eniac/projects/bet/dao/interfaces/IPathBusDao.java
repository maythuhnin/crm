package com.eniac.projects.bet.dao.interfaces;

import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathBusBean;

public interface IPathBusDao {

	public void insert(PathBusBean pathBus) throws MyBatisException;
	
	public void delete(int pathId) throws MyBatisException;
	
}
