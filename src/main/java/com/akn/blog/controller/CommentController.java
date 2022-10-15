package com.akn.blog.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akn.blog.payload.CommentDTO;
import com.akn.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// Create Comment
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
			@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
	}

	// Get Comment By PostID
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getCommentByPostId(@PathVariable Long postId) {
		return commentService.getCommentByPostId(postId);
	}

	// Get Comment By CommentID
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public CommentDTO getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
		return commentService.getCommentById(postId, commentId);
	}

	// Update Comment
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			@Valid @RequestBody CommentDTO commentDTO) {
		CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	// Delete Comment By PostId and CommentId
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_EDITOR" })
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}

}
