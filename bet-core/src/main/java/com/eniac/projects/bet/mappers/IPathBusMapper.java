package com.eniac.projects.bet.mappers;

import com.eniac.projects.bet.model.PathBusBean;

public interface IPathBusMapper {
	
	int insertPathBus(PathBusBean pathBus);
	
	int deletePathBus(int pathId);

}
