package com.studyclub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyclub.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserNickname(String userNickname);
	Optional<User> findByUserNicknameAndUserPw(String userNickname, String userPw);
}
