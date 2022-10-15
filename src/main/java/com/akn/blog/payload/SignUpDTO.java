package com.akn.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignUpDTO {

	@NotEmpty
	@Size(min = 3, message = "Name should have at least 3 characters")
	private String name;

	@NotEmpty
	@Size(min = 3, message = "Username should have at least 3 characters")
	private String username;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(min = 8, message = "Password should have at least 8 characters")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SignUpDTO(String name, String username, String email, String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public SignUpDTO() {
	}

	@Override
	public String toString() {
		return "SignUpDTO [name=" + name + ", username=" + username + ", email=" + email + ", password=" + password
				+ "]";
	}

}
