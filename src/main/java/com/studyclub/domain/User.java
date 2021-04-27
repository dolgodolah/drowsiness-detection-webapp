package com.studyclub.domain;

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
	private String nickname;

	
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자와 특수문자를 포함한 숫자 8자 이상 입력해주세요.")
	@Column(nullable = false)
	private String password;
	
	@Column
	private Long studytime;
	
	@PrePersist
	public void prePersist() {
		this.studytime=this.studytime == null ? 0 : this.studytime;
	}

	@Builder
	public User(Long id, String nickname, String password) {
		this.id=id;
		this.nickname = nickname;
		this.password = password;
	}
	
	
	public void saveStudytime(Long time) {
		this.studytime+=time;
	}
	

}
