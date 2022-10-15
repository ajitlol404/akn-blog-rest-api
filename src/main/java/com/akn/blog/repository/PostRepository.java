package com.akn.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akn.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
