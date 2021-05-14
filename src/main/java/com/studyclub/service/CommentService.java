package com.studyclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyclub.domain.Comment;
import com.studyclub.domain.Post;
import com.studyclub.domain.User;
import com.studyclub.repository.CommentRepository;

@Service
public class CommentService {
	
	private final CommentRepository commentRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	@Transactional
	public Long saveComment(Comment comment) {
		return commentRepository.save(comment).getId();
	}
	
	public List<Comment> getComments(Post post){
		return commentRepository.findByPostId(post.getId());
	}
	
}
