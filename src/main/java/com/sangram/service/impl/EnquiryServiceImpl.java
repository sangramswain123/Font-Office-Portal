package com.sangram.service.impl;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangram.binding.DashboardResponse;
import com.sangram.binding.EnquiryForm;
import com.sangram.binding.SearchForm;
import com.sangram.entity.StudentEnquiryEntity;
import com.sangram.entity.UserDetailsEntity;
import com.sangram.repository.CourseRepo;
import com.sangram.repository.EnquiryStatusRepo;
import com.sangram.repository.StudentEnquiryRepo;
import com.sangram.repository.UserDetailsRepo;
import com.sangram.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo statusRepo;
	
	@Autowired
	private UserDetailsRepo userRepo;
	
	@Autowired
	private StudentEnquiryRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	
	
	@Override
	public List<String> getCourses() {
		return courseRepo.getAllCourses();
	}

	@Override
	public List<String> getEnquiryStatus() {
		return statusRepo.getAllStatus();
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		DashboardResponse response = new DashboardResponse();
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();
			List<StudentEnquiryEntity> enquiries = userEntity.getEnquiries();
			
			Integer totalCnt = enquiries.size();
			Integer enrollCnt = enquiries.stream()
								.filter(e -> e.getEnquiryStatus().equals("Enrolled"))
								.collect(Collectors.toList())
								.size();
			Integer lostCnt = enquiries.stream()
								.filter(e -> e.getEnquiryStatus().equals("Lost"))
								.collect(Collectors.toList())
								.size();
			
			response.setTotalEnquiriesCnt(totalCnt);
			response.setEnrolledCnt(enrollCnt);
			response.setLostCnt(lostCnt);
			
		}
		return response;
	}

	@Override
	public boolean addEnquiry(EnquiryForm form) {
		StudentEnquiryEntity enqEntity = new StudentEnquiryEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId =(Integer) session.getAttribute("userId");
		UserDetailsEntity userEntity = userRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		enqRepo.save(enqEntity);
		
		return true;
	}

	@Override
	public List<StudentEnquiryEntity> getAllEnquaries(Integer userId) {
		List<StudentEnquiryEntity> enqList = enqRepo.findByUser_Id(userId);
		return enqList;
	}

	@Override
	public List<StudentEnquiryEntity> getFilteredEnquaries(SearchForm form, Integer userId) {
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();
			List<StudentEnquiryEntity> enquiries = userEntity.getEnquiries();
			
			if(null!=form.getCourse() && !"".equals(form.getCourse())) {
				enquiries = enquiries.stream()
						.filter(e -> e.getClassName().equals(form.getCourse()))
						.collect(Collectors.toList());
			}
			if(null!=form.getStatus() && !"".equals(form.getStatus())) {
				enquiries = enquiries.stream()
						.filter(e -> e.getEnquiryStatus().equals(form.getStatus()))
						.collect(Collectors.toList());
			}
			if(null!=form.getClassMode() && !"".equals(form.getClassMode())) {
				enquiries = enquiries.stream()
						.filter(e -> e.getClassMode().equals(form.getClassMode()))
						.collect(Collectors.toList());
			}
			return enquiries;
		}
		return null;
	}

	@Override
	public EnquiryForm editStudentEnquary(Integer enquaryId) {
		Optional<StudentEnquiryEntity> enquary = enqRepo.findById(enquaryId);
		StudentEnquiryEntity entity = enquary.get();
		EnquiryForm form = new EnquiryForm();
		form.setStudentName(entity.getStudentName());
		form.setMobileNo(entity.getMobileNo());
		form.setClassMode(entity.getClassMode());
		form.setClassName(entity.getClassName());
		form.setEnquiryStatus(entity.getEnquiryStatus());
		form.setId(entity.getEnquiryId());
		return form;
	}

	@Override
	public StudentEnquiryEntity handleEditEnquiry(Integer id, EnquiryForm form) {
		Optional<StudentEnquiryEntity> enquiryEntity = enqRepo.findById(id);
		StudentEnquiryEntity entity = enquiryEntity.get();
		entity.setStudentName(form.getStudentName());
		entity.setMobileNo(form.getMobileNo());
		entity.setClassMode(form.getClassMode());
		entity.setClassName(form.getClassName());
		entity.setEnquiryStatus(form.getEnquiryStatus());
		
		StudentEnquiryEntity editEnquiry = enqRepo.save(entity);
		return editEnquiry;
	}
	
	

}
