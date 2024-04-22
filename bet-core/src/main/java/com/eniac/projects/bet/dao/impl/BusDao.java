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
import com.eniac.projects.bet.dao.interfaces.IBusDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IBusMapper;
import com.eniac.projects.bet.model.BusBean;

@Repository
public class BusDao implements IBusDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(BusBean bus) throws MyBatisException {
		logger.info("=====> Inserting busBean : " + bus + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			busMapper.insertBus(bus);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Bus : " + bus, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Bus : " + bus, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allBus <=====");
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			return busMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Bus", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public BusBean selectByCriteria(Map<String, Object> criteria) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting busByBusName <=====");
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			return busMapper.selectByCriteria(criteria);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting Bus By Criteria : " + criteria, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(BusBean bus) throws MyBatisException {
		logger.info("=====> Updating busBean : " + bus + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			busMapper.updateBus(bus);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Bus : " + bus, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Bus : " + bus, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int busId) throws MyBatisException {
		logger.info("=====> Setting inactive busId : " + busId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			busMapper.deleteBus(busId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Bus : " + busId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Bus : " + busId, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public BusBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting busById <=====");
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			return busMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting Bus By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<BusBean> selectForDropDown() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IBusMapper busMapper = sqlSession.getMapper(IBusMapper.class);
			return busMapper.selectForDropDown();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown", e);
		} finally {
			sqlSession.close();
		}
	}

}
