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

import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	private HttpSession session;
	public UserController(UserService userService, HttpSession session) {
		this.session=session;
		this.userService=userService;
	}
	
	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(@Valid UserForm userForm, BindingResult result, Model model) {
		//입력하지 않은 칸이 있는지 확인한다.
		if (result.hasErrors()) {
			return "/user/login";
		}
		
		
		//입력받은 로그인 정보에 대한 유저 유효한지 확인한다.
		User user = new User();
		user.setNickname(userForm.getNickname());
		user.setPassword(userForm.getPassword());
		
		if (userService.login(user)) {
			session.setAttribute("USER", userForm);
			session.setMaxInactiveInterval(20); //세션 2시간 유지 
			return "redirect:/";
		}
		//닉네임이 존재하지 않거나 비밀번호 틀림
		model.addAttribute("inval", "존재하지 않는 닉네임이거나, 틀린 비밀번호입니다.");
		return "/user/login";
	}
	
	
	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("user", new User());
		return "/user/join";
	}
	
	@PostMapping("/join")
	public String join(@Valid User user, BindingResult result, Model model) {
		//닉네임이나 비밀번호가 형식에 맞지 않다면 에러 메세지를 출력한다.
		if(result.hasErrors()) {
			return "/user/join";
		}
		
		try {
			userService.join(user);
		}catch (Exception e) {
			//이미 존재하는 닉네임
			model.addAttribute("inval", e.getMessage());
			return "/user/join";
		}
		
		//회원가입 성공
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout() {
		//로그아웃 시 세션을 초기화한다.
		session.invalidate();
		return "redirect:/";
	}

}
