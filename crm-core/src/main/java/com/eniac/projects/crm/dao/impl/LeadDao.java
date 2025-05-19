package com.eniac.projects.crm.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.crm.config.MyBatisSqlSessionFactory;
import com.eniac.projects.crm.dao.interfaces.ILeadDao;
import com.eniac.projects.crm.exception.MyBatisException;
import com.eniac.projects.crm.mappers.ILeadMapper;
import com.eniac.projects.crm.model.LeadBean;

@Repository
public class LeadDao implements ILeadDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(LeadBean lead) throws MyBatisException {
		logger.info("=====> Inserting leadBean : " + lead + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			ILeadMapper leadMapper = sqlSession.getMapper(ILeadMapper.class);
			leadMapper.insert(lead);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Lead : " + lead, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Lead : " + lead, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allLead <=====");
			ILeadMapper leadMapper = sqlSession.getMapper(ILeadMapper.class);
			return leadMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Lead", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(LeadBean lead) throws MyBatisException {
		logger.info("=====> Updating leadBean : " + lead + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			ILeadMapper leadMapper = sqlSession.getMapper(ILeadMapper.class);
			leadMapper.update(lead);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Lead : " + lead, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Lead : " + lead, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public LeadBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting leadById <=====");
			ILeadMapper leadMapper = sqlSession.getMapper(ILeadMapper.class);
			return leadMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting Lead By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}

}
