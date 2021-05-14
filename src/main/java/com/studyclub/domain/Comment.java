package com.studyclub.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	
	@NotBlank(message="댓글 내용을 입력해주세요.")
	@Length(max=150, message="댓글은 150자를 초과할 수 없습니다.")
	private String content;
	
	private String author;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	public void setUser(User user) {
		this.user=user;
		this.author=user.getNickname();
		user.getComments().add(this);
	}
	
	public void setPost(Post post) {
		this.post=post;
		post.getComments().add(this);
	}
	
}
