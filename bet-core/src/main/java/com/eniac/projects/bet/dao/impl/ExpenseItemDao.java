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
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allExpenseItem <=====");
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			return expenseItemMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all ExpenseItem", e);
		} finally {
			sqlSession.close();
		}
	}

	

	@Override
	public void delete(int dailyExpenseId) throws MyBatisException {
		logger.info("=====> Deleting expenseItemId : " + dailyExpenseId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IExpenseItemMapper expenseItemMapper = sqlSession.getMapper(IExpenseItemMapper.class);
			expenseItemMapper.deleteExpenseItem(dailyExpenseId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting ExpenseItem : " + dailyExpenseId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting ExpenseItem : " + dailyExpenseId, e);
		} finally {
			sqlSession.close();
		}

	}

}
