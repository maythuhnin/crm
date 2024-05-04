package com.eniac.projects.bet.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IInventoryDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IInventoryMapper;
import com.eniac.projects.bet.model.InventoryBean;

@Repository
public class InventoryDao implements IInventoryDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(InventoryBean inventory) throws MyBatisException {
		logger.info("=====> Inserting inventoryBean : " + inventory + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			inventoryMapper.insertInventory(inventory);
			sqlSession.commit();
			return inventory.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Inventory : " + inventory, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Inventory : " + inventory, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allInventory <=====");
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			return inventoryMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Inventory", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public InventoryBean selectById(int inventoryId) throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectById <=====");
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			return inventoryMapper.selectById(inventoryId);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectById", e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public List<InventoryBean> selectForDropDown() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting selectForDropDown <=====");
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			return inventoryMapper.selectForDropDown();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting selectForDropDown", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void update(InventoryBean inventory) throws MyBatisException {
		logger.info("=====> Updating inventoryBean : " + inventory + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			inventoryMapper.updateInventory(inventory);
			sqlSession.commit();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when updating Inventory : " + inventory, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when updating Inventory : " + inventory, e);
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public void delete(int inventoryId) throws MyBatisException {
		logger.info("=====> Setting inactive inventoryId : " + inventoryId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IInventoryMapper inventoryMapper = sqlSession.getMapper(IInventoryMapper.class);
			inventoryMapper.deleteInventory(inventoryId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Inventory : " + inventoryId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Inventory : " + inventoryId, e);
		} finally {
			sqlSession.close();
		}

	}


}
