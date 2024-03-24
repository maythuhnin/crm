package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.sql.Date;

public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Mode {
		ADD, EDIT, DELETE, PASSWORD;
	}
	
	private int id;
	private int updatedId;
	private Date updatedDateTime;
	private Mode mode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUpdatedId() {
		return updatedId;
	}

	public void setUpdatedId(int updatedId) {
		this.updatedId = updatedId;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
