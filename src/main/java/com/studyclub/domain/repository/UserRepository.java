package com.studyclub.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyclub.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByNickname(String nickname);
}
