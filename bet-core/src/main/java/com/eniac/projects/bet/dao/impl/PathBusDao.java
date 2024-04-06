package com.eniac.projects.bet.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IPathBusDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IPathBusMapper;
import com.eniac.projects.bet.model.PathBusBean;

@Repository
public class PathBusDao implements IPathBusDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void insert(PathBusBean pathBus) throws MyBatisException {
		logger.info("=====> Inserting pathBusBean : " + pathBus + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathBusMapper pathBusMapper = sqlSession.getMapper(IPathBusMapper.class);
			pathBusMapper.insertPathBus(pathBus);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting PathBus : " + pathBus, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting PathBus : " + pathBus, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void delete(int pathId) throws MyBatisException {
		logger.info("=====> Deleting pathBus with pathId : " + pathId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathBusMapper pathBusMapper = sqlSession.getMapper(IPathBusMapper.class);
			pathBusMapper.deletePathBus(pathId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting PathBus with pathId : " + pathId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting PathBus with pathId: " + pathId, e);
		} finally {
			sqlSession.close();
		}

	}

}
