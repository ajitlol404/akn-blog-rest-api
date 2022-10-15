package com.akn.blog.service;

import java.util.List;

import com.akn.blog.payload.CommentDTO;

public interface CommentService {

	// Create Comment for Post
	CommentDTO createComment(Long postId, CommentDTO commentDTO);

	// Get All Comment For PostID
	List<CommentDTO> getCommentByPostId(Long postId);

	// Get Comment By CommentId and PostId
	CommentDTO getCommentById(Long postId, Long commentId);

	// Update Comment
	CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

	// Delete Comment
	void deleteComment(Long postId, Long commentId);

}
