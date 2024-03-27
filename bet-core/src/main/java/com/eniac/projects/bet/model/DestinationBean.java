package com.eniac.projects.bet.model;

import java.io.Serializable;

public class DestinationBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Boolean isOrder;
	private Boolean status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Boolean isOrder) {
		this.isOrder = isOrder;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("DestinationBean [name=%s, isOrder=%s, status=%s]", name, isOrder, status);
	}

}
