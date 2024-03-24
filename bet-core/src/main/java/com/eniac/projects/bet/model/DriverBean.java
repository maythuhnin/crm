package com.eniac.projects.bet.model;

import java.io.Serializable;

public class DriverBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Double loanAmount;
	private Boolean status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("DriverBean [name=%s, loanAmount=%s, status=%s]", name, loanAmount, status);
	}

}
