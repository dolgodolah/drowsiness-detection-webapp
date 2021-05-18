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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studyclub.domain.Comment;
import com.studyclub.domain.Post;
import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.service.CommentService;
import com.studyclub.service.PostService;
import com.studyclub.service.UserService;


@Controller
public class PostController {
	
	private final HttpSession session;
	private final PostService postService;
	private final UserService userService;
	private final CommentService commentService;
	
	
	@Autowired
	public PostController(HttpSession session, PostService postService, UserService userService, CommentService commentService) {
		this.session = session;
		this.postService = postService;
		this.userService = userService;
		this.commentService = commentService;
	}

	@GetMapping("/board")
	public String board(Model model, @PageableDefault(size=5, sort="createdAt", direction = Direction.DESC) Pageable pageable) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser!=null) {
			Page<Post> posts = postService.getPosts(pageable);
			model.addAttribute("posts", posts);
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
		postService.savePost(post);
		return "redirect:/board";
	}
	
	
	
	
	@GetMapping("/board/{postId}")
	public String detail(@PathVariable("postId") Long postId, Model model, Comment comment) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser!=null) {
			Post post = postService.getPost(postId);
			List<Comment> comments = commentService.getComments(post);
			model.addAttribute("user", loginUser);
			model.addAttribute("post", post);
			model.addAttribute("comment", comment);
			model.addAttribute("comments", comments);
			return "/board/detail";
		}
		return "redirect:/login";
	}
	
	@DeleteMapping("/board/{postId}")
	public String delete(@PathVariable("postId") Long postId) {
		postService.deletePost(postService.getPost(postId));
		return "redirect:/board";
	}
	
	@GetMapping("/board/edit/{postId}")
	public String edit(@PathVariable("postId") Long postId, Model model) {
		model.addAttribute("post",postService.getPost(postId));
		return "/board/edit";
	}
	
	@PutMapping("/board/edit/{postId}")
	public String edit(@PathVariable("postId") Long postId ,Post editedPost) {
		Post post = postService.getPost(postId);
		post.edit(editedPost);
		postService.savePost(post);
		return "redirect:/board/{postId}";
	}
	
	@GetMapping("/board/search")
	public String search(@PageableDefault(size=5, sort="createdAt", direction = Direction.DESC) Pageable pageable, Model model,String keyword) {
		Page<Post> posts = postService.search(pageable, keyword);
		model.addAttribute("posts", posts);
		model.addAttribute("isFirst", posts.isFirst());
		model.addAttribute("isLast", posts.isLast());
		
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		return "/board/search";
	}
}
