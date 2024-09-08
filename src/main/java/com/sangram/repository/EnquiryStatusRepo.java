package com.sangram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sangram.entity.EnquiryStatusEntity;

public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatusEntity, Integer>{
	
	@Query("SELECT s.statusName FROM EnquiryStatusEntity s")
	List<String> getAllStatus();
}
