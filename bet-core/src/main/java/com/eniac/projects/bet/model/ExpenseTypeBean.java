package com.eniac.projects.bet.model;

import java.io.Serializable;

public class ExpenseTypeBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("ExpenseTypeBean [name=%s]", name);
	}

}
