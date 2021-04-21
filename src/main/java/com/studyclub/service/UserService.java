package com.studyclub.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyclub.domain.entity.User;
import com.studyclub.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findOne(String userNickname) {
		return userRepository.findByUserNickname(userNickname);
	}
	
	@Transactional
	public Long save(User user) {
		return userRepository.save(user).getId();
	}
	
	public Optional<User> checkLogin(User user) {
		return userRepository.findByUserNicknameAndUserPw(user.getUserNickname(),user.getUserPw());
	}
	

}
