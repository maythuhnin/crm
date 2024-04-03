package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.util.List;

public class PathBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String path;
	private String bus;

	private List<PathExpenseBean> pathExpenseList;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public List<PathExpenseBean> getPathExpenseList() {
		return pathExpenseList;
	}

	public void setPathExpenseList(List<PathExpenseBean> pathExpenseList) {
		this.pathExpenseList = pathExpenseList;
	}

	@Override
	public String toString() {
		return String.format("PathBean [path=%s, bus=%s, pathExpenseList=%s]", path, bus, pathExpenseList);
	}

}
