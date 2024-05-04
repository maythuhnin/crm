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
import com.eniac.projects.bet.dao.interfaces.IDailyExpenseDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IDailyExpenseMapper;
import com.eniac.projects.bet.model.DailyExpenseBean;

@Repository
public class DailyExpenseDao implements IDailyExpenseDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(DailyExpenseBean dailyExpense) throws MyBatisException {
		logger.info("=====> Inserting dailyExpenseBean : " + dailyExpense + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			dailyExpenseMapper.insertDailyExpense(dailyExpense);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting DailyExpense : " + dailyExpense, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting DailyExpense : " + dailyExpense, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable(Map<String,Object> criteria) throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allDailyExpense <=====");
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			return dailyExpenseMapper.selectForDatatable(criteria);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all DailyExpense", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<Object> selectForIncomeDatatable(Map<String,Object> criteria) throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting incomeDatatable <=====");
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			return dailyExpenseMapper.selectForIncomeDatatable(criteria);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all incomeDatatable", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(DailyExpenseBean dailyExpense) throws MyBatisException {
		logger.info("=====> Updating dailyExpenseBean : " + dailyExpense + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			dailyExpenseMapper.updateDailyExpense(dailyExpense);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating DailyExpense : " + dailyExpense, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating DailyExpense : " + dailyExpense, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int dailyExpenseId) throws MyBatisException {
		logger.info("=====> Setting inactive dailyExpenseId : " + dailyExpenseId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			dailyExpenseMapper.deleteDailyExpense(dailyExpenseId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting DailyExpense : " + dailyExpenseId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting DailyExpense : " + dailyExpenseId, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public DailyExpenseBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting dailyExpenseById <=====");
			IDailyExpenseMapper dailyExpenseMapper = sqlSession.getMapper(IDailyExpenseMapper.class);
			return dailyExpenseMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting DailyExpense By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}
	

}
