package com.eniac.projects.crm.model;

import java.io.Serializable;

public class UserBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String newPassword;
	private String name;
	private String role;
	private Boolean status;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("UserBean [username=%s, password=%s, newPassword=%s, name=%s, role=%s, status=%s]",
				username, password, newPassword, name, role, status);
	}

}
