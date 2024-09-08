package com.sangram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangram.entity.UserDetailsEntity;

public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer>{
	
	public UserDetailsEntity findByEmail(String email);
	
	public UserDetailsEntity findByEmailAndPassword(String email, String password);
}
