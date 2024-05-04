package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class InventoryBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String item;
	private Date receivedDate;
	private String receivedDateAsString;
	private Double price;
	private Integer quantity;

	private List<StockBean> stockList;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedDateAsString() {
		return receivedDateAsString;
	}

	public void setReceivedDateAsString(String receivedDateAsString) {
		String[] receivedDateArr = receivedDateAsString.split("/");
		String yyyymmdd = receivedDateArr[2] + "-" + receivedDateArr[1] + "-" + receivedDateArr[0];
		this.receivedDate = java.sql.Date.valueOf(yyyymmdd);
		this.receivedDateAsString = receivedDateAsString;
	}

	public List<StockBean> getStockList() {
		return stockList;
	}

	public void setStockList(List<StockBean> stockList) {
		this.stockList = stockList;
	}

	@Override
	public String toString() {
		return String.format(
				"InventoryBean [item=%s, receivedDate=%s, receivedDateAsString=%s, price=%s, quantity=%s, stockList=%s]",
				item, receivedDate, receivedDateAsString, price, quantity, stockList);
	}

}
