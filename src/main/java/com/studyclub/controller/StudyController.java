package com.studyclub.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.repository.UserRepository;
import com.studyclub.service.UserService;

@Controller
public class StudyController {
	
	UserService userService;
	UserRepository userRepository;
	HttpSession session;
	
	@Autowired
	public StudyController(UserService userService, UserRepository userRepository, HttpSession session) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.session=session;
	}



	@GetMapping("/study")
	public String study(Model model) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser==null) {
			model.addAttribute("userForm", new UserForm());
			return "/user/login";
		}
		return "/study/study";
	}
	
	@PostMapping("/saveStudyTime")
	public String saveStudyTime(Long studyTime) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		User user = userService.findOne(loginUser.getNickname()).get();
		user.saveStudytime(studyTime);
		userRepository.save(user);
		return "redirect:/";
	}
}
