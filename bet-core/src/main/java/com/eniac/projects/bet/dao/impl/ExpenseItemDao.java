package com.eniac.projects.bet.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IExpenseItemDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IExpenseItemMapper;
import com.eniac.projects.bet.model.ExpenseItemBean;

@Repository
public class ExpenseItemDao implements IExpenseItemDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(ExpenseItemBean expenseItem) throws MyBatisException {
		logger.info("=====> Inserting expenseItemBean : " + expenseItem + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			expenseItemMapper.insertExpenseItem(expenseItem);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting ExpenseItem : " + expenseItem, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting ExpenseItem : " + expenseItem, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable(int dailyExpenseId) throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allExpenseItem <=====");
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			return expenseItemMapper.selectForDatatable(dailyExpenseId);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all ExpenseItem", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public int delete(int id) throws MyBatisException {
		logger.info("=====> Deleting id : " + id + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		int deleted = 0;
		try {
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			deleted = expenseItemMapper.delete(id);
			sqlSession.commit();
			return deleted;
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting ExpenseItem : " + id, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting ExpenseItem : " + id, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public int deleteByExpenseId(int dailyExpenseId) throws MyBatisException {
		logger.info("=====> Deleting expenseItemId : " + dailyExpenseId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		int deleted = 0;
		try {
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			deleted = expenseItemMapper.deleteExpenseItem(dailyExpenseId);
			sqlSession.commit();
			return deleted;
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting ExpenseItem : " + dailyExpenseId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting ExpenseItem : " + dailyExpenseId, e);
		} finally {
			sqlSession.close();
		}

	}
	
	@Override
	public ExpenseItemBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectById <=====");
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			return expenseItemMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all selectById", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<ExpenseItemBean> selectByExpenseId(int dailyExpenseId) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectByExpenseId <=====");
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			return expenseItemMapper.selectByExpenseId(dailyExpenseId);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all selectByExpenseId", e);
		} finally {
			sqlSession.close();
		}
	}

}
