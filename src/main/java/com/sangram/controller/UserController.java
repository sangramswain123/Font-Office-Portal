package com.sangram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangram.binding.ForgotPassword;
import com.sangram.binding.LoginForm;
import com.sangram.binding.SignupForm;
import com.sangram.binding.UnlockForm;
import com.sangram.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignupForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignupForm form, Model model) {
		boolean status = userService.signup(form);
		if(status) {
			model.addAttribute("succMsg", "Check Your Mail");
		}else {
			model.addAttribute("errMsg", "Provide A Unique Mail");
		}
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam("email") String email, Model model) {
		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock",form);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlock") UnlockForm form, Model model) {
		String result = userService.unlockAccount(form);
		model.addAttribute("result", result);
		return "unlock";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String handleLoginPage(@ModelAttribute("user") LoginForm form, Model model) {
		boolean status = userService.login(form);
		if(status) {
			model.addAttribute("succMsg", "Congratulations Login Successfull");
			return "redirect:/dashboard";
		}
		
		model.addAttribute("errMsg", "Please Check Your Email & Password");
		return "login";
	}
	
	@GetMapping("/forgot")
	public String forgotPwdPage(Model model) {
		model.addAttribute("forgot", new ForgotPassword());
		return "forgot";
	}
	
	@PostMapping("/forgot")
	public String handlingForgotPassword(@ModelAttribute("forgot") ForgotPassword forgotPwd, Model model) {
		System.out.println("forgot password : "+ forgotPwd);
		boolean status = userService.forgotPwd(forgotPwd);
		if(status) {
			model.addAttribute("succMsg", "Please Check Your Email For Password");
		}else {
			model.addAttribute("errMsg", "Please Enter Valid Email ID");
		}
		return "forgot";
	}
}
