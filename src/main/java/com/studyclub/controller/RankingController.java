package com.studyclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	public String rank(Model model, @PageableDefault(size=5, sort="studytime",direction=Sort.Direction.DESC) Pageable pageable) {

		//현재 page에 대한 정보(Page request [number: 0, size 5, sort: studytime: DESC])를 담아 userService로 보낸다.
		model.addAttribute("users",userService.rankUser(pageable)); 
		
		//view에 있는 previous, next에 각각 이전페이지 번호와 다음페이지 번호를 담아보낸다.
		model.addAttribute("previous",pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next",pageable.next().getPageNumber());
		
		/*
		 * JPA pageable을 이용해 5개씩 가져오기때문에 순위를 매길 때 1,2,3,4,5가 반복된다.
		 * page=0일 때는 0*5=0, page=1일 때는 1*5=5, page=2일 때는 2*5=10을 1,2,3,4,5에 더해준다..
		 */
		model.addAttribute("page",pageable.getPageNumber()*pageable.getPageSize());
		
		return "/ranking/ranking";
		
	}
}
