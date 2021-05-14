package com.studyclub.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.studyclub.domain.Comment;
import com.studyclub.domain.Post;
import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.service.CommentService;
import com.studyclub.service.PostService;
import com.studyclub.service.UserService;

@Controller
public class CommentController {
	
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;
	private final HttpSession session;
	
	@Autowired
	public CommentController(UserService userService, PostService postService, HttpSession session, CommentService commentService) {
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
		this.session = session;
	}



	@PostMapping("/board/{postId}")
	public String comment(@PathVariable("postId") Long postId, @Valid Comment comment, BindingResult result, Model model) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if(result.hasErrors()) {
			Post post = postService.getPost(postId);
			List<Comment> comments = commentService.getComments(post);
			model.addAttribute("user", loginUser);
			model.addAttribute("post", post);
			model.addAttribute("comments", comments);
			return "/board/detail";
		}
		User user = userService.findOne(loginUser.getNickname()).get();
		Post post = postService.getPost(postId);
		comment.setUser(user);
		comment.setPost(post);
		commentService.saveComment(comment);
		return "redirect:/board/{postId}";
	}
}
