package com.sangram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangram.binding.DashboardResponse;
import com.sangram.binding.EnquiryForm;
import com.sangram.binding.SearchForm;
import com.sangram.entity.StudentEnquiryEntity;
import com.sangram.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logoutPage() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId =(Integer) session.getAttribute("userId");
		DashboardResponse dashboard = enqService.getDashboardData(userId);
		model.addAttribute("dashBoardData", dashboard);
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		model.addAttribute("courses", enqService.getCourses());
		model.addAttribute("statusName", enqService.getEnquiryStatus());
		model.addAttribute("enquiryData", new EnquiryForm());
		return "add-enquiry";
	}
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enquiryData") EnquiryForm form, Model model) {
		System.out.println("enquiryForm : "+ form);
		boolean status = enqService.addEnquiry(form);
		if(status) {
			model.addAttribute("succMsg", "Form filled Successfully");
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		return "add-enquiry";
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiryPage(Model model) {
		model.addAttribute("courses", enqService.getCourses());
		model.addAttribute("statusName", enqService.getEnquiryStatus());
		model.addAttribute("searchForm", new SearchForm());
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnquiryEntity> enqList = enqService.getAllEnquaries(userId);
		model.addAttribute("enquiryData", enqList);
		return "view-enquiries";
	}
	
	@GetMapping("/filtered-enquiries")
	public String viewFilteredEnquiry(@RequestParam String course, @RequestParam String status, @RequestParam String mode, Model model) {
		SearchForm search = new SearchForm();
		search.setCourse(course);
		search.setStatus(status);
		search.setClassMode(mode);
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnquiryEntity> enqList = enqService.getFilteredEnquaries(search, userId);
		model.addAttribute("enquiryData", enqList);
		return "filtered-enquries";
	}
	
	@GetMapping("/enquiry/{id}")
	public String editEnquiry(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("courses", enqService.getCourses());
		model.addAttribute("statusName", enqService.getEnquiryStatus());
		model.addAttribute("editEnquiry", enqService.editStudentEnquary(id));
		return "edit";
	}
	
	@PostMapping("/enquiry/{id}")
	public String handleEditEnquiry(@PathVariable("id") Integer id, 
			@ModelAttribute("editEnquiry") EnquiryForm form, Model model) {
		enqService.handleEditEnquiry(id, form);
		return "redirect:/enquiries";
	}
	
}
