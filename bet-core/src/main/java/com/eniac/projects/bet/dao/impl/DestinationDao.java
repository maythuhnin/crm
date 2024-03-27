package com.eniac.projects.bet.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IDestinationDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IDestinationMapper;
import com.eniac.projects.bet.model.DestinationBean;

@Repository
public class DestinationDao implements IDestinationDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(DestinationBean destination) throws MyBatisException {
		logger.info("=====> Inserting destinationBean : " + destination + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDestinationMapper destinationMapper = sqlSession.getMapper(IDestinationMapper.class);
			destinationMapper.insertDestination(destination);
			sqlSession.commit();
			return destination.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Destination : " + destination, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Destination : " + destination, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allDestination <=====");
			IDestinationMapper destinationMapper = sqlSession.getMapper(IDestinationMapper.class);
			return destinationMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Destination", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<DestinationBean> selectForDropDown() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IDestinationMapper destinationMapper = sqlSession.getMapper(IDestinationMapper.class);
			return destinationMapper.selectForDropDown();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(DestinationBean destination) throws MyBatisException {
		logger.info("=====> Updating destinationBean : " + destination + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDestinationMapper destinationMapper = sqlSession.getMapper(IDestinationMapper.class);
			destinationMapper.updateDestination(destination);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Destination : " + destination, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Destination : " + destination, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int destinationId) throws MyBatisException {
		logger.info("=====> Setting inactive destinationId : " + destinationId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDestinationMapper destinationMapper = sqlSession.getMapper(IDestinationMapper.class);
			destinationMapper.deleteDestination(destinationId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Destination : " + destinationId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Destination : " + destinationId, e);
		} finally {
			sqlSession.close();
		}

	}

}
