package com.sangram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sangram.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {
	
	@Query("SELECT c.courseName FROM CourseEntity c")
	List<String> getAllCourses();
}
