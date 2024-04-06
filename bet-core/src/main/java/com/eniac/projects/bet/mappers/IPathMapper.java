package com.eniac.projects.bet.mappers;

import java.util.List;
import java.util.Map;

import com.eniac.projects.bet.model.PathBean;

public interface IPathMapper {
	
	int insertPath(PathBean path);
	
	int updatePath(PathBean path);
	
	int deletePath(int id);
	
	List<Object> selectForDatatable();

	List<Object> selectForDropDown(Map<String,Object> criteria);

	PathBean selectById(int id);

}
