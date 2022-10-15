package com.akn.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akn.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
