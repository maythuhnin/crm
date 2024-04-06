package com.eniac.projects.bet.mappers;

import java.util.List;
import com.eniac.projects.bet.model.StockBean;

public interface IStockMapper {
	
	int insertStock(StockBean stock);
	
	int deleteStock(int id);
	
	List<Object> selectForDatatable();
	
}
