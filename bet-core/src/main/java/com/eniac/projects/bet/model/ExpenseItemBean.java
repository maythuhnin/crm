package com.eniac.projects.bet.model;

import java.io.Serializable;

public class ExpenseItemBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer dailyExpenseId;
	private Integer expenseTypeId;
	private Integer inventoryId;
	private Double amount;
	private Integer quantity;
	private String expenseType;

	public Integer getDailyExpenseId() {
		return dailyExpenseId;
	}

	public void setDailyExpenseId(Integer dailyExpenseId) {
		this.dailyExpenseId = dailyExpenseId;
	}

	public Integer getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Integer expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format(
				"ExpenseItemBean [dailyExpenseId=%s, expenseTypeId=%s, inventoryId=%s, amount=%s, quantity=%s, expenseType=%s]",
				dailyExpenseId, expenseTypeId, inventoryId, amount, quantity, expenseType);
	}

}
