package com.studyclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.studyclub.domain.entity.User;
import com.studyclub.service.UserService;

@Controller
public class RankingController {

	UserService userService;
	
	@Autowired
	public RankingController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/ranking")
	public String rank(Model model) {
		List<User> users = userService.rankUser();
		model.addAttribute("users", users);
		return "/ranking/ranking";
		
	}
}
