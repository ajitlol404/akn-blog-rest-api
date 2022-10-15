package com.akn.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {
	private Long id;
	@NotEmpty
	@Size(min = 2, message = "Name should have at least 2 characters and cannot be empty")
	private String name;

	@NotEmpty
	@Email(message = "Enter valid email")
	private String email;

	@NotEmpty
	@Size(min = 10, message = "Body should have at least 10 characters and cannot be empty")
	private String body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public CommentDTO(Long id,
			@NotEmpty @Size(min = 2, message = "Name should have at least 2 characters and cannot be empty") String name,
			@NotEmpty @Email(message = "Enter valid email") String email,
			@NotEmpty @Size(min = 10, message = "Body should have at least 10 characters and cannot be empty") String body) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}

	public CommentDTO() {
	}

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", name=" + name + ", email=" + email + ", body=" + body + "]";
	}

}
