package com.eniac.projects.bet.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.eniac.projects.bet.config.MyBatisSqlSessionFactory;
import com.eniac.projects.bet.dao.interfaces.IStockDao;
import com.eniac.projects.bet.exception.MyBatisException;
import com.eniac.projects.bet.mappers.IStockMapper;
import com.eniac.projects.bet.model.StockBean;

@Repository
public class StockDao implements IStockDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(StockBean stock) throws MyBatisException {
		logger.info("=====> Inserting stockBean : " + stock + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IStockMapper stockMapper = sqlSession.getMapper(IStockMapper.class);
			stockMapper.insertStock(stock);
			sqlSession.commit();
			return stock.getId();
		} catch (DuplicateKeyException e) {
			throw new MyBatisException("DuplicateKeyException occured when inserting Stock : " + stock, e);
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when inserting Stock : " + stock, e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Object> selectForDatatable() throws MyBatisException {

		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			logger.info("=====> Selecting allStock <=====");
			IStockMapper stockMapper = sqlSession.getMapper(IStockMapper.class);
			return stockMapper.selectForDatatable();
		} catch (Exception e) {
			throw new MyBatisException("Mybatis Exception occured when selecting all Stock", e);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void delete(int inventoryId) throws MyBatisException {
		logger.info("=====> Deleting stock with InventoryId : " + inventoryId + "<=====");
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			IStockMapper stockMapper = sqlSession.getMapper(IStockMapper.class);
			stockMapper.deleteStock(inventoryId);
			sqlSession.commit();
		} catch (DataIntegrityViolationException e) {
			throw new MyBatisException("DataIntegrityViolationException occured when deleting Stock : " + inventoryId, e);
		} catch (Exception e) {
			throw new MyBatisException("Exception occured when deleting Stock : " + inventoryId, e);
		} finally {
			sqlSession.close();
		}

	}


}
