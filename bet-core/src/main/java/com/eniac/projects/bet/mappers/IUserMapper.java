package com.eniac.projects.bet.mappers;

import java.util.List;
import java.util.Map;

import com.eniac.projects.bet.model.UserBean;

public interface IUserMapper {
	
	int insertUser(UserBean user);
	
	int updateUser(UserBean user);
	
	int updateLoggedIn(int id);
	
	int deleteUser(int id);
	
	List<Object> selectForDatatable();
	
	UserBean selectByCriteria(Map<String, Object> criteria);

	UserBean selectById(int id);

}
