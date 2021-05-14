package com.studyclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyclub.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPostId(Long id);
}
