package com.studyclub.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studyclub.domain.Post;
import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.service.PostService;
import com.studyclub.service.UserService;


@Controller
public class PostController {
	
	private final HttpSession session;
	private final PostService postService;
	private final UserService userService;
	
	
	@Autowired
	public PostController(HttpSession session, PostService postService, UserService userService) {
		this.session = session;
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping("/board")
	public String board(Model model, @PageableDefault(size=5, sort="createdAt", direction = Direction.DESC) Pageable pageable) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser!=null) {
			Page<Post> posts = postService.getPosts(pageable);
			model.addAttribute("posts", postService.getPosts(pageable));
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			model.addAttribute("isFirst", posts.isFirst());
			model.addAttribute("isLast", posts.isLast());
			
			
			return "/board/board";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/board/post")
	public String post(Model model, Post post) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser!=null) {
			model.addAttribute("post", post);
			return "/board/post";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/board/post")
	public String post(Post post) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		User user = userService.findOne(loginUser.getNickname()).get();
		post.setUser(user);
		postService.addPost(post);
		return "redirect:/board";
	}
	
	
	@GetMapping("/board/{postId}")
	public String detail(@PathVariable("postId") Long postId, Model model) {
		model.addAttribute("post", postService.getPost(postId));
		
		return "/board/detail";
	}
}
