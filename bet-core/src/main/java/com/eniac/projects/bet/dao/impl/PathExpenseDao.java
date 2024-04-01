package com.eniac.projects.bet.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IPathExpenseDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IPathExpenseMapper;
import com.eniac.projects.bet.model.PathExpenseBean;

@Repository
public class PathExpenseDao implements IPathExpenseDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(PathExpenseBean pathExpense) throws MyBatisException {
		logger.info("=====> Inserting pathExpenseBean : " + pathExpense + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathExpenseMapper pathExpenseMapper = sqlSession.getMapper(IPathExpenseMapper.class);
			pathExpenseMapper.insertPathExpense(pathExpense);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting PathExpense : " + pathExpense, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting PathExpense : " + pathExpense, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IPathExpenseMapper pathExpenseMapper = sqlSession.getMapper(IPathExpenseMapper.class);
			return pathExpenseMapper.selectForDatatable(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDatatable with pathId : " + id, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void delete(int pathId) throws MyBatisException {
		logger.info("=====> Deleting pathExpense with pathId : " + pathId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathExpenseMapper pathExpenseMapper = sqlSession.getMapper(IPathExpenseMapper.class);
			pathExpenseMapper.deletePathExpense(pathId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting PathExpense with pathId : " + pathId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting PathExpense with pathId: " + pathId, e);
		} finally {
			sqlSession.close();
		}

	}

}
