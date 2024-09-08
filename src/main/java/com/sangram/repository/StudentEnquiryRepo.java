package com.sangram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangram.entity.StudentEnquiryEntity;

public interface StudentEnquiryRepo extends JpaRepository<StudentEnquiryEntity, Integer>{
	
	List<StudentEnquiryEntity> findByUser_Id(Integer userId);
}
