package com.techverse.response;

import com.techverse.model.User;

public class UserSignUpResponse {

	
	private boolean status;
	private String message;
	private String token;
	private User user;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserSignUpResponse(boolean status, String message, User user) {
		super();
		this.status = status;
		this.message = message;
		this.user = user;
	}
	public UserSignUpResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
