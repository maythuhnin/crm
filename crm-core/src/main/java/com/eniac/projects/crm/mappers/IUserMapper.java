package com.eniac.projects.crm.mappers;

import java.util.List;
import java.util.Map;

import com.eniac.projects.crm.model.UserBean;

public interface IUserMapper {
	
	int insertUser(UserBean user);
	
	int updateUser(UserBean user);
	
	int updateLoggedIn(int id);
	
	int deleteUser(int id);
	
	List<Object> selectForDatatable();
	
	UserBean selectByCriteria(Map<String, Object> criteria);

	UserBean selectById(int id);

}
