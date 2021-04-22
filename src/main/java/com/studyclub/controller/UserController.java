package com.studyclub.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.studyclub.domain.entity.User;
import com.studyclub.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/")
	public String main(HttpSession session) {
		return "index";
	}
	
	@GetMapping("/user/login")
	public String login() {
		return "/user/login";
	}
	
	@PostMapping("/user/login")
	public String login(Model model, User user, HttpSession session) {
		
		//입력받은 로그인 정보에 대한 유저가 존재하는지 확인한다.
		if (userService.checkLogin(user)) {
			//세션을 해당 유저의 id로 설정한다.
			session.setAttribute("id", user.getNickname());
			return "redirect:/";
		}
		else {
			model.addAttribute("result","error");
			return "/user/login";
			
		}
	}
	
	
	@GetMapping("/user/join")
	public String join(User user) {
		return "/user/join";
	}
	
	@PostMapping("/user/join")
	public String join(@Valid User user, BindingResult result, Model model) {
		//아이디나 비밀번호가 유효하지 않다면 해당 에러 메세지를 출력한다.
		if(result.hasErrors()) {
			return "/user/join";
		}
		//해당 아이디가 이미 존재하는 경우.
		else if(userService.findOne(user.getNickname()).isPresent()) {
			model.addAttribute("isPresent","true");
			return "/user/join";
		}
		
		//정상적으로 회원가입 처리가 될 경우.
		model.addAttribute("result","success");
		userService.save(user);
	
		return "/user/login";
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		//로그아웃 시 세션을 초기화한다.
		session.invalidate();
		return "redirect:/";
	}

}
