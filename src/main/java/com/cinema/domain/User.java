package com.cinema.domain;

public class User {
	private int userId;
	private String userName;
	private String password;
	private String emailId;
	private String role;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int l) {
		this.userId = l;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
