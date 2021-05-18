package com.studyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyclub.domain.Post;
import com.studyclub.repository.PostRepository;

@Service
public class PostService {
	
	PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	
	
	public Page<Post> getPosts(Pageable pageable){
		return postRepository.findAll(pageable);
	}
	
	@Transactional
	public Long savePost(Post post) {
		return postRepository.save(post).getId();
	}
	
	public Post getPost(Long id) {
		return postRepository.getOne(id);
	}
	
	@Transactional
	public void deletePost(Post post) {
		postRepository.delete(post);
	}
	
	public Page<Post> search(Pageable pageable, String keyword){
		return postRepository.findByTitleContaining(pageable, keyword);
	}
	
}
