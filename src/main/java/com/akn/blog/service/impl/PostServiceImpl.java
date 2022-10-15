package com.akn.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akn.blog.entity.Post;
import com.akn.blog.exception.ResourceNotFoundException;
import com.akn.blog.payload.PostDTO;
import com.akn.blog.payload.PostResponse;
import com.akn.blog.repository.PostRepository;
import com.akn.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper modelMapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	// Create Post
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		Post post = mapToEntity(postDTO);

		Post newPost = postRepository.save(post);

		PostDTO postResponse = mapToDto(newPost);

		return postResponse;
	}

	// Get All Posts
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = postRepository.findAll(pageable);

		List<Post> listOfPosts = posts.getContent();

		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setIsLast(posts.isLast());
		return postResponse;

	}

	// Get Post By Id
	@Override
	public PostDTO getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return mapToDto(post);

	}

	// Update Post By Id
	@Override
	public PostDTO updatePost(Long id, PostDTO postDTO) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	// Delete Post By Id
	@Override
	public void deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);

	}

	// Convert Entity To Dto
	private PostDTO mapToDto(Post post) {
		PostDTO postDTO = modelMapper.map(post, PostDTO.class);
		return postDTO;
	}

	// Convert Dto to Entity
	private Post mapToEntity(PostDTO postDTO) {
		Post post = modelMapper.map(postDTO, Post.class);
		return post;
	}

}
