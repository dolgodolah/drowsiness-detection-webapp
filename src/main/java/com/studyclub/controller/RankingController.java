package com.studyclub.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.studyclub.domain.User;
import com.studyclub.dto.UserForm;
import com.studyclub.service.UserService;

@Controller
public class RankingController {

	private final UserService userService;
	private final HttpSession session;
	
	@Autowired
	public RankingController(UserService userService, HttpSession session) {
		this.userService = userService;
		this.session = session;
	}


	@GetMapping("/ranking")
	public String rank(Model model, @PageableDefault(size=5, sort="studyTime",direction=Sort.Direction.DESC) Pageable pageable) {
		UserForm loginUser = (UserForm) session.getAttribute("USER");
		if (loginUser != null) {
			
			//현재 pageable에 해당하는 유저들을 가져온다.
			Page<User> users = userService.rankUser(pageable);
			model.addAttribute("users", users);
			//해당 users가 페이지의 첫번째 값이거나 마지막값이면 이에 해당하는 처리를 한다.
			model.addAttribute("isFirst", users.isFirst());
			model.addAttribute("isLast",users.isLast());
			//view에 있는 previous, next에 각각 이전페이지 번호와 다음페이지 번호를 담아보낸다.
			model.addAttribute("previous",pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next",pageable.next().getPageNumber());
			
			/*
			 * JPA pageable을 이용해 user를 5개씩 가져오기때문에 ${userStat.index+1}는 1,2,3,4,5가 반복된다.
			 * pageNumber=0일 때 pageSize를 곱해주면 0*5=0, pageNumber=1일 때는 1*5=5, pageNumber=2일 때는 2*5=10이다.
			 * ${userStat.index+1+page} 즉, 기존 1,2,3,4,5 반복에서 pageNumber*pageSize를 더해줌으로써 모든 사용자의 순위를 매길 수 있다.
			 */
			model.addAttribute("page",pageable.getPageNumber()*pageable.getPageSize());
			
			return "/ranking/ranking";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/ranking/search")
	public String searchRanking(String nickname, Model model) {
		Optional<User> user = userService.findOne(nickname);
		if (user.isPresent()) {
			int index = userService.searchRanking(nickname).indexOf(user.get());
			model.addAttribute("index", index+1);
			model.addAttribute("user", user.get());
		}
		
		
		
		return "/ranking/search";
	}
}
