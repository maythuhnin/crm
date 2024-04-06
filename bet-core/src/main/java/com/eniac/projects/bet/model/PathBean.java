package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.util.List;

public class PathBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String path;

	private List<PathBusBean> pathBusList;
	private List<PathExpenseBean> pathExpenseList;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<PathBusBean> getPathBusList() {
		return pathBusList;
	}

	public void setPathBusList(List<PathBusBean> pathBusList) {
		this.pathBusList = pathBusList;
	}

	public List<PathExpenseBean> getPathExpenseList() {
		return pathExpenseList;
	}

	public void setPathExpenseList(List<PathExpenseBean> pathExpenseList) {
		this.pathExpenseList = pathExpenseList;
	}

	@Override
	public String toString() {
		return String.format("PathBean [path=%s, pathBusList=%s, pathExpenseList=%s]", path, pathBusList,
				pathExpenseList);
	}

}
