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

import com.studyclub.domain.entity.User;
import com.studyclub.domain.repository.UserRepository;

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
	public Long save(User user) {
		return userRepository.save(user).getId();
	}
	
	public boolean checkLogin(User user) {
		Optional<User> result = userRepository.findByNickname(user.getNickname());
	
		//입력받은 닉네임이 존재하고, 비밀번호도 일치하면 true를 반환한다.
		if(result.isPresent()&&result.get().getPassword().equals(user.getPassword()))return true;
		else return false;
	}
	
	public Page<User> rankUser(Pageable pageable){
		return userRepository.findAll(pageable);
	}

}
