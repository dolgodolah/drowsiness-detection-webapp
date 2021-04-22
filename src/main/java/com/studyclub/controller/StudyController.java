package com.studyclub.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.studyclub.domain.entity.User;
import com.studyclub.domain.repository.UserRepository;
import com.studyclub.service.UserService;

@Controller
public class StudyController {
	
	UserService userService;
	
	@Autowired
	public StudyController(UserService userService) {
		this.userService = userService;
	}



	@GetMapping("/study")
	public String study(Model model, HttpSession session) {
		return "/study/study";
	}
	
	
	

	@PostMapping("/saveStudyTime")
	public String saveStudyTime(Long studyTime, HttpSession session, User user) {
		String nickname = session.getAttribute("id").toString();
		user = userService.findOne(nickname).get();
		user.saveStudytime(studyTime);
		userService.save(user);
		return "redirect:/";
	}
}
