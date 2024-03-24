package com.eniac.projects.bet.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IUserDao;
import com.eniac.projects.bet.exception.DuplicatedEntryException;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IUserMapper;
import com.eniac.projects.bet.model.UserBean;

@Repository
public class UserDao implements IUserDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(UserBean user) throws MyBatisException {
		logger.info("=====> Inserting userBean : " + user + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			userMapper.insertUser(user);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting User : " + user, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting User : " + user, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allUser <=====");
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			return userMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all User", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public UserBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting userByUserName <=====");
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			return userMapper.selectByCriteria(criteria);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting User By Criteria : " + criteria, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(UserBean user) throws MyBatisException {
		logger.info("=====> Updating userBean : " + user + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			userMapper.updateUser(user);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating User : " + user, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating User : " + user, e);
		} finally {
			sqlSession.close();
		}

	}
	
	@Override
	public void updateLoggedIn(int id) throws MyBatisException, DuplicatedEntryException {
		logger.info("=====> Updating loggedInTime : " + id + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			userMapper.updateLoggedIn(id);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating loggedInTime : " + id, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating loggedInTime : " + id, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int userId) throws MyBatisException {
		logger.info("=====> Setting inactive userId : " + userId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			userMapper.deleteUser(userId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting User : " + userId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting User : " + userId, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public UserBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting userById <=====");
			IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
			return userMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting User By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}

}
