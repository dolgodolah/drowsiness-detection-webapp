package com.studyclub.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Pattern(regexp="^[a-zA-Z0-9_]{5,12}$", message="5~12자의 영문 소문자, 숫자와 특수기호(_)만 사용 가능합니다.")
	private String userNickname;

	
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자와 특수문자를 포함한 숫자 8자 이상 입력해주세요.")
	@Column(nullable = false)
	private String userPw;
	
	@Column
	private Long studyTime;
	
	@PrePersist
	public void prePersist() {
		this.studyTime=this.studyTime == null ? 0 : this.studyTime;
	}

	@Builder
	public User(Long id, String userNickname, String userPw) {
		this.id=id;
		this.userNickname = userNickname;
		this.userPw = userPw;
	}
	
	
	public void saveStudyTime(Long time) {
		this.studyTime+=time;
	}
	

}
