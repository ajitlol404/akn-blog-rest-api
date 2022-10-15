package com.akn.blog.payload;

public class AuthResponse {

	private String accessToken;
	private String email;
	private String tokenType = "Bearer";

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public AuthResponse() {
	}

	@Override
	public String toString() {
		return "AuthResponse [accessToken=" + accessToken + ", email=" + email + ", tokenType=" + tokenType + "]";
	}

	public AuthResponse(String accessToken, String email, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.email = email;
		this.tokenType = tokenType;
	}

	// ------------------------------------------------------------------------------------------
	public AuthResponse(String accessToken, String email) {
		super();
		this.accessToken = accessToken;
		this.email = email;
	}
}
