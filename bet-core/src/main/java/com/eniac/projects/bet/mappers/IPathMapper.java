package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.PathBean;

public interface IPathMapper {
	
	int insertPath(PathBean path);
	
	int updatePath(PathBean path);
	
	int deletePath(int id);
	
	List<Object> selectForDatatable();

	List<PathBean> selectForDropDown();

	PathBean selectById(int id);

}
