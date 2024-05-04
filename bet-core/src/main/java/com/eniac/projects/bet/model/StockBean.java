package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.sql.Date;

public class StockBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer inventoryId;
	private Integer quantity;
	private Boolean stockIn;
	private Date transactionDate;
	private String transactionDateAsString;
	private String transactionRef;

	public StockBean() {
		super();
	}

	public StockBean(Integer inventoryId, Integer quantity, Boolean stockIn, Date transactionDate) {
		super();
		this.inventoryId = inventoryId;
		this.quantity = quantity;
		this.stockIn = stockIn;
		this.transactionDate = transactionDate;
	}
	
	public StockBean(Integer inventoryId, Integer quantity, Boolean stockIn, Date transactionDate, String transactionRef) {
		super();
		this.inventoryId = inventoryId;
		this.quantity = quantity;
		this.stockIn = stockIn;
		this.transactionDate = transactionDate;
		this.transactionRef = transactionRef;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getStockIn() {
		return stockIn;
	}

	public void setStockIn(Boolean stockIn) {
		this.stockIn = stockIn;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDateAsString() {
		return transactionDateAsString;
	}

	public void setTransactionDateAsString(String transactionDateAsString) {
		String[] transactionDateArr = transactionDateAsString.split("/");
		String yyyymmdd = transactionDateArr[2] + "-" + transactionDateArr[1] + "-" + transactionDateArr[0];
		this.transactionDate = java.sql.Date.valueOf(yyyymmdd);
		this.transactionDateAsString = transactionDateAsString;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

	@Override
	public String toString() {
		return String.format(
				"StockBean [inventoryId=%s, quantity=%s, stockIn=%s, transactionDate=%s, transactionDateAsString=%s, transactionRef=%s]",
				inventoryId, quantity, stockIn, transactionDate, transactionDateAsString, transactionRef);
	}

	
}
