package com.eniac.projects.bet.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IExpenseTypeDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IExpenseTypeMapper;
import com.eniac.projects.bet.model.ExpenseTypeBean;

@Repository
public class ExpenseTypeDao implements IExpenseTypeDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(ExpenseTypeBean expenseType) throws MyBatisException {
		logger.info("=====> Inserting expenseTypeBean : " + expenseType + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			expenseTypeMapper.insertExpenseType(expenseType);
			sqlSession.commit();
			return expenseType.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting ExpenseType : " + expenseType, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting ExpenseType : " + expenseType, e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public int update(ExpenseTypeBean expenseType) throws MyBatisException {
		logger.info("=====> Updating expenseTypeBean : " + expenseType + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			expenseTypeMapper.updateExpenseType(expenseType);
			sqlSession.commit();
			return expenseType.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when Updating ExpenseType : " + expenseType, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when Updating ExpenseType : " + expenseType, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public ExpenseTypeBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectById <=====");
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			return expenseTypeMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectById ", e);
		} finally {
			sqlSession.close();
		}
	}
	

	@Override
	public List<ExpenseTypeBean> selectForDropDown() throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			return expenseTypeMapper.selectForDropDown();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown ", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<ExpenseTypeBean> selectForDataTable() throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDataTable <=====");
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			return expenseTypeMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDataTable ", e);
		} finally {
			sqlSession.close();
		}
	}


	@Override
	public void delete(int expenseTypeId) throws MyBatisException {
		logger.info("=====> Setting inactive expenseTypeId : " + expenseTypeId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IExpenseTypeMapper expenseTypeMapper = sqlSession.getMapper(IExpenseTypeMapper.class);
			expenseTypeMapper.deleteExpenseType(expenseTypeId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting ExpenseType : " + expenseTypeId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting ExpenseType : " + expenseTypeId, e);
		} finally {
			sqlSession.close();
		}

	}

}
