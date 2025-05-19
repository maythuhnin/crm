package com.eniac.projects.crm.model;

import java.io.Serializable;

public class LeadBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum LeadStatus {
		OK, REPAIRING, DELETED
	}

	private String contactName;
	private String phone;
	private String email;
	private String facebook;
	private String remark;
	private LeadStatus status;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LeadStatus getStatus() {
		return status;
	}

	public void setStatus(LeadStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BusBean [contactName=" + contactName + ", phone=" + phone + ", email=" + email + ", facebook="
				+ facebook + ", remark=" + remark + ", status=" + status + "]";
	}

}
