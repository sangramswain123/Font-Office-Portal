package com.sangram.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sangram.entity.CourseEntity;
import com.sangram.entity.EnquiryStatusEntity;
import com.sangram.repository.CourseRepo;
import com.sangram.repository.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryStatusRepo statusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java Fullstack");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python Fullstack");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("Devops");
		
		courseRepo.saveAll(Arrays.asList(c1, c2, c3));
		
		statusRepo.deleteAll();
		
		EnquiryStatusEntity e1 = new EnquiryStatusEntity();
		e1.setStatusName("New");
		
		EnquiryStatusEntity e2 = new EnquiryStatusEntity();
		e2.setStatusName("Enrolled");
		
		EnquiryStatusEntity e3 = new EnquiryStatusEntity();
		e3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(e1, e2, e3));
	}

}
