package com.eniac.projects.bet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eniac.projects.bet.dao.interfaces.IUserDao;
import com.eniac.projects.bet.exception.BuisnessException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.model.UserBean;

@Service
public class UserServiceImpl {

	@Autowired
	private IUserDao userDao;

	public void createUser(UserBean user)
			throws MyBatisException, BuisnessException {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		user.setPassword(encoder.encode(user.getPassword()));

		userDao.insert(user);

	}

	public void updateUser(UserBean user)
			throws MyBatisException, BuisnessException {

		try {
			if (null != user.getPassword() && !user.getPassword().isEmpty()) {
				PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

				user.setPassword(encoder.encode(user.getPassword()));
			}

			userDao.update(user);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating User : " + user, e);
		}
	}

	public void updateLoggedIn(int id)
			throws MyBatisException, BuisnessException {

		try {

			userDao.updateLoggedIn(id);
		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating updateLoggedIn : " + id, e);
		}
	}

	public void updatePassword(UserBean user)
			throws MyBatisException, BuisnessException {
		try {

			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

			UserBean oldUser = userDao.selectById(user.getId());

			if (encoder.matches(user.getPassword(), oldUser.getPassword())) {
				oldUser.setPassword(encoder.encode(user.getNewPassword()));
				userDao.update(oldUser);
			}

		} catch (Exception e) {
			throw new BuisnessException("Business Exception occured when updating User : " + user, e);
		}
	}

	public List<Object> selectForDatatable() throws MyBatisException {
		return userDao.selectForDatatable();
	}

	public UserBean selectById(int id) throws MyBatisException {
		return userDao.selectById(id);
	}

	public UserBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException {
		return userDao.selectByCriteria(criteria);
	}

	public void deleteUser(int userId) throws MyBatisException {
		userDao.delete(userId);
	}
}
