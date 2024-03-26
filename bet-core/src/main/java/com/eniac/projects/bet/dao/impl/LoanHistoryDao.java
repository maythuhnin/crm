package com.eniac.projects.bet.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.ILoanHistoryDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.ILoanHistoryMapper;
import com.eniac.projects.bet.model.LoanHistoryBean;

@Repository
public class LoanHistoryDao implements ILoanHistoryDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(LoanHistoryBean loanHistory) throws MyBatisException {
		logger.info("=====> Inserting loanHistoryBean : " + loanHistory + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			ILoanHistoryMapper loanHistoryMapper = sqlSession.getMapper(ILoanHistoryMapper.class);
			loanHistoryMapper.insertLoanHistory(loanHistory);
			sqlSession.commit();
			return loanHistory.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting LoanHistory : " + loanHistory, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting LoanHistory : " + loanHistory, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable(int driverId) throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allLoanHistory <=====");
			ILoanHistoryMapper loanHistoryMapper = sqlSession.getMapper(ILoanHistoryMapper.class);
			return loanHistoryMapper.selectForDatatable(driverId);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all LoanHistory", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public void update(LoanHistoryBean loanHistory) throws MyBatisException {
		logger.info("=====> Updating loanHistoryBean : " + loanHistory + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			ILoanHistoryMapper loanHistoryMapper = sqlSession.getMapper(ILoanHistoryMapper.class);
			loanHistoryMapper.updateLoanHistory(loanHistory);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating LoanHistory : " + loanHistory, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating LoanHistory : " + loanHistory, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int loanHistoryId) throws MyBatisException {
		logger.info("=====> Setting inactive loanHistoryId : " + loanHistoryId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			ILoanHistoryMapper loanHistoryMapper = sqlSession.getMapper(ILoanHistoryMapper.class);
			loanHistoryMapper.deleteLoanHistory(loanHistoryId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting LoanHistory : " + loanHistoryId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting LoanHistory : " + loanHistoryId, e);
		} finally {
			sqlSession.close();
		}

	}


}
