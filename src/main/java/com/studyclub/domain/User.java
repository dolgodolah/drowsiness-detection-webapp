package com.studyclub.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(nullable = false)
	@Pattern(regexp="^[a-zA-Z0-9_]{5,12}$", message="5~12자의 영문 소문자, 숫자와 특수기호(_)만 사용 가능합니다.")
	private String nickname;

	
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자와 특수문자를 포함한 숫자 8자 이상 입력해주세요.")
	@Column(nullable = false)
	private String password;
	
	@Column
	private boolean studying;
	
	@Column
	private Long studyTime;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();  
	
	@PrePersist
	public void prePersist() {
		this.studyTime=this.studyTime == null ? 0 : this.studyTime;
	}

	
	
	public void saveStudytime(Long time) {
		this.studyTime+=time;
	}
	
	public void updateStudying() {
		if (this.isStudying()) {
			this.studying=false;
		}else {
			this.studying=true;
		}
	}
	

}
