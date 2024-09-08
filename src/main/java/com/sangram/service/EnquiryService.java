package com.sangram.service;

import java.util.List;

import com.sangram.binding.DashboardResponse;
import com.sangram.binding.EnquiryForm;
import com.sangram.binding.SearchForm;
import com.sangram.entity.StudentEnquiryEntity;

public interface EnquiryService {
	
	public List<String> getCourses();
	
	public List<String> getEnquiryStatus();
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public boolean addEnquiry(EnquiryForm e);	
	
	public List<StudentEnquiryEntity> getAllEnquaries(Integer userId);
	
	public List<StudentEnquiryEntity> getFilteredEnquaries(SearchForm form, Integer userId);
	
	public EnquiryForm editStudentEnquary(Integer enquaryId);
	
	public StudentEnquiryEntity handleEditEnquiry(Integer id, EnquiryForm form);
	
}
