package com.studyclub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studyclub.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	Page<Post> findByTitleContaining(Pageable pageable, String Title);
}
