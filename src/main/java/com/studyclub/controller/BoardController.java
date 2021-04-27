package com.studyclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BoardController {
	
	@GetMapping("/board")
	public String list() {
		return "/board/board";
	}
	
}
