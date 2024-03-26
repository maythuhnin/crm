package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.sql.Date;

public class LoanHistoryBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum LoanType {
		LOAN, RETURN;
	}

	private Integer driverId;
	private Date loanDate;
	private String loanDateAsString;
	private Double amount;
	private LoanType type;
	private String description;

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanDateAsString() {
		return loanDateAsString;
	}

	public void setLoanDateAsString(String loanDateAsString) {
		String[] loanDateArr = loanDateAsString.split("/");
		String yyyymmdd = loanDateArr[2] + "-" + loanDateArr[1] + "-" + loanDateArr[0];
		this.loanDate = java.sql.Date.valueOf(yyyymmdd);
		this.loanDateAsString = loanDateAsString;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LoanType getType() {
		return type;
	}

	public void setType(LoanType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format(
				"LoanHistoryBean [driverId=%s, loanDate=%s, loanDateAsString=%s, amount=%s, type=%s, description=%s]",
				driverId, loanDate, loanDateAsString, amount, type, description);
	}

}
