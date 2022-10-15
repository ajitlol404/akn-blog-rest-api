package com.akn.blog.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akn.blog.payload.PostDTO;
import com.akn.blog.payload.PostResponse;
import com.akn.blog.service.PostService;
import com.akn.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	// Create Post
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}

	// Get All Post
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@GetMapping
	public PostResponse getAllPost(
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// Get Post By Id
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Update Post By Id
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) {
		return ResponseEntity.ok(postService.updatePost(id, postDTO));
	}

	// Delete Post By Id
	@RolesAllowed("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.ok("Post deleted successfully");
	}

}
