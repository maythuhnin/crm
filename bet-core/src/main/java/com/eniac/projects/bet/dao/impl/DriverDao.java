package com.eniac.projects.bet.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IDriverDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IDriverMapper;
import com.eniac.projects.bet.model.DriverBean;

@Repository
public class DriverDao implements IDriverDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(DriverBean bus) throws MyBatisException {
		logger.info("=====> Inserting busBean : " + bus + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			busMapper.insertDriver(bus);
			sqlSession.commit();
			return bus.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Driver : " + bus, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Driver : " + bus, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allDriver <=====");
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			return busMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Driver", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<DriverBean> selectForDropDown() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			return busMapper.selectForDropDown();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(DriverBean bus) throws MyBatisException {
		logger.info("=====> Updating busBean : " + bus + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			busMapper.updateDriver(bus);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Driver : " + bus, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Driver : " + bus, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int busId) throws MyBatisException {
		logger.info("=====> Setting inactive busId : " + busId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			busMapper.deleteDriver(busId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Driver : " + busId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Driver : " + busId, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public DriverBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting busById <=====");
			IDriverMapper busMapper = sqlSession.getMapper(IDriverMapper.class);
			return busMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting Driver By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}

}
