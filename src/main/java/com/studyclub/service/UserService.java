package com.studyclub.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studyclub.domain.User;
import com.studyclub.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findOne(String nickname) {
		return userRepository.findByNickname(nickname);
	}
	
	@Transactional
	public Long join(User user) {
		validateDuplicateUser(user);
		return userRepository.save(user).getId();
	}
	
	private void validateDuplicateUser(User user) {
		if (userRepository.findByNickname(user.getNickname()).isPresent()) {
			throw new IllegalStateException("이미 존재하는 닉네임입니다.");
		}
	}
	
	public boolean login(User user) {
		Optional<User> result = userRepository.findByNickname(user.getNickname());
	
		//입력받은 닉네임이 존재하고, 비밀번호도 일치하면 true를 반환한다.
		if(result.isPresent()&&result.get().getPassword().equals(user.getPassword()))return true;
		else return false;
	}
	
	public Page<User> rankUser(Pageable pageable){
		return userRepository.findAll(pageable);
	}

}
