package com.eniac.projects.bet.model;

import java.io.Serializable;

public class BusBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum BusStatus {
		OK, SERVICING, DELETED
	}

	private String licensePlate;
	private Integer primaryDriverId;
	private Integer secondaryDriverId;
	private String phone;
	private BusStatus status;

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Integer getPrimaryDriverId() {
		return primaryDriverId;
	}

	public void setPrimaryDriverId(Integer primaryDriverId) {
		this.primaryDriverId = primaryDriverId;
	}

	public Integer getSecondaryDriverId() {
		return secondaryDriverId;
	}

	public void setSecondaryDriverId(Integer secondaryDriverId) {
		this.secondaryDriverId = secondaryDriverId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BusStatus getStatus() {
		return status;
	}

	public void setStatus(BusStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("BusBean [licensePlate=%s, primaryDriverId=%s, secondaryDriverId=%s, phone=%s, status=%s]",
				licensePlate, primaryDriverId, secondaryDriverId, phone, status);
	}

}
