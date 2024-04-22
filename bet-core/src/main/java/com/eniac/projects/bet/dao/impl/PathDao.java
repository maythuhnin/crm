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
import com.eniac.projects.bet.dao.interfaces.IPathDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IPathMapper;
import com.eniac.projects.bet.model.PathBean;

@Repository
public class PathDao implements IPathDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(PathBean path) throws MyBatisException {
		logger.info("=====> Inserting pathBean : " + path + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			pathMapper.insertPath(path);
			sqlSession.commit();
			return path.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Path : " + path, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Path : " + path, e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public void update(PathBean path) throws MyBatisException {
		logger.info("=====> Updating pathBean : " + path + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			pathMapper.updatePath(path);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Path : " + path, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Path : " + path, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allPath <=====");
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			return pathMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Path", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDropDown(Map<String,Object> criteria) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			return pathMapper.selectForDropDown(criteria);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown ", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void delete(int pathId) throws MyBatisException {
		logger.info("=====> Setting inactive pathId : " + pathId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			pathMapper.deletePath(pathId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Path : " + pathId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Path : " + pathId, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public PathBean selectById(int id) throws MyBatisException {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting pathById <=====");
			IPathMapper pathMapper = sqlSession.getMapper(IPathMapper.class);
			return pathMapper.selectById(id);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting Path By Id : " + id, e);
		} finally {
			sqlSession.close();
		}
	}

}
