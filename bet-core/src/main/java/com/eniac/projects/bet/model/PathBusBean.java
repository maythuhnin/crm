package com.eniac.projects.bet.model;

import java.io.Serializable;

public class PathBusBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pathId;
	private Integer busId;

	public Integer getPathId() {
		return pathId;
	}

	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	@Override
	public String toString() {
		return String.format("PathBusBean [pathId=%s, busId=%s]", pathId, busId);
	}

}
