package com.studyclub.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
	@NotBlank(message="닉네임을 입력해주세요.")
	private String nickname;
	
	@NotBlank(message="비밀번호를 입력해주세요.")
	private String password;
}
