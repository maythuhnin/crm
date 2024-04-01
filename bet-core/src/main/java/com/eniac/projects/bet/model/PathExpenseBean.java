package com.eniac.projects.bet.model;

import java.io.Serializable;

public class PathExpenseBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pathId;
	private Integer expenseId;
	private Double amount;

	public Integer getPathId() {
		return pathId;
	}

	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("PathExpenseBean [pathId=%s, expenseId=%s, amount=%s]", pathId, expenseId, amount);
	}

}
