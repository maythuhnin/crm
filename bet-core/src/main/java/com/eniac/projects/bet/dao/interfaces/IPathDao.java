package com.eniac.projects.bet.dao.interfaces;

import java.util.List;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.PathBean;

public interface IPathDao {

	public int insert(PathBean path) throws MyBatisException;
	
	public void delete(int pathId) throws MyBatisException;
	
	public void update(PathBean path) throws MyBatisException;
	
	public List<Object> selectForDatatable() throws MyBatisException;
	
	public List<PathBean> selectForDropDown() throws MyBatisException;
	
	public PathBean selectById(int id) throws MyBatisException;
	
}
