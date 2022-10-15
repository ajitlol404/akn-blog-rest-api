package com.akn.blog.payload;

public class AuthRequest {
	private String usernameOrEmail;
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthRequest() {
	}

	public AuthRequest(String usernameOrEmail, String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthRequest [usernameOrEmail=" + usernameOrEmail + ", password=" + password + "]";
	}
	
	
}
