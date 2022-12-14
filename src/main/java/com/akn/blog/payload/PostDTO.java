package com.akn.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO {

	private Long id;

	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;

	@NotEmpty
	private String content;

	private Set<CommentDTO> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public PostDTO(Long id,
			@NotEmpty @Size(min = 2, message = "Post title should have at least 2 characters") String title,
			@NotEmpty @Size(min = 10, message = "Post description should have at least 10 characters") String description,
			@NotEmpty String content, Set<CommentDTO> comments) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
	}

	public PostDTO() {
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", comments=" + comments + "]";
	}

}
