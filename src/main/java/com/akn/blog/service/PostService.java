package com.akn.blog.service;

import com.akn.blog.payload.PostDTO;
import com.akn.blog.payload.PostResponse;

public interface PostService {

	// Create Post
	PostDTO createPost(PostDTO postDTO);

	// Get All Post
	PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

	// Get Post By Id
	PostDTO getPostById(Long id);

	// Update Post By Id
	PostDTO updatePost(Long id, PostDTO postDTO);

	// Delete Post By Id
	void deletePost(Long id);

}
