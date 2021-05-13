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
		Page<Post> page = postRepository.findAll(pageable);
		if (page.isLast()) {
			return postRepository.findAll(page.nextOrLastPageable());
		}
		
		return postRepository.findAll(pageable);
	}
	
	@Transactional
	public Long addPost(Post post) {
		return postRepository.save(post).getId();
	}
	
	public Post getPost(Long id) {
		return postRepository.getOne(id);
	}
	
}
