package com.sangram.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangram.binding.ForgotPassword;
import com.sangram.binding.LoginForm;
import com.sangram.binding.SignupForm;
import com.sangram.binding.UnlockForm;
import com.sangram.entity.UserDetailsEntity;
import com.sangram.repository.UserDetailsRepo;
import com.sangram.service.UserService;
import com.sangram.util.EmailUtils;
import com.sangram.util.PasswordUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private EmailUtils emailUtil;
	
	@Autowired
	private UserDetailsRepo userRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean signup(SignupForm form) {
		UserDetailsEntity userDtl = userRepo.findByEmail(form.getEmail());
		if(userDtl != null) {
			return false;
		}
		UserDetailsEntity user = new UserDetailsEntity();
		BeanUtils.copyProperties(form, user);
		String tempPassword = PasswordUtils.passwordGenerator();
		user.setPassword(tempPassword);
		user.setAccStatus("LOCKED");
		userRepo.save(user);
		//setup email details
		String to = user.getEmail();
		String subject = "Unlock Your Account | Ashok IT";
		StringBuffer body = new StringBuffer();
		body.append("<h1>Use below temporary password to unlock your account.</h1>");
		body.append("Temporary password : " + tempPassword);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account</a>");
		emailUtil.sendMail(to, subject, body.toString());
		return true;
	}

	@Override
	public String unlockAccount(UnlockForm form) {
		UserDetailsEntity entity = userRepo.findByEmail(form.getEmail());
		if(!(entity.getPassword()).equals(form.getTempPwd())) {
			return "Enter Correct Temporary Password";
		}
		if(!(form.getNewPwd()).equals(form.getCnfPwd())) {
			return "New Password And Confirm Password Are Not Same";
		}
		entity.setAccStatus("UNLOCKED");
		entity.setPassword(form.getNewPwd());
		userRepo.save(entity);
		return "Congratulation Your Account UNLOCKED";
	}
	
	@Override
	public boolean login(LoginForm form) {
		UserDetailsEntity entity = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());
		if((entity != null) && ((entity.getAccStatus()).equals("UNLOCKED"))) {
			session.setAttribute("userId", entity.getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean forgotPwd(ForgotPassword form) {
		UserDetailsEntity entity = userRepo.findByEmail(form.getEmail());
		if(entity == null) {
			return false;
		}
		String to = form.getEmail();
		String subject = "Your Password ! Ashok IT";
		StringBuilder body = new StringBuilder();
		body.append("<h2>Please Use The Below Password While Login.</h2>");
		body.append("<br/>");
		body.append("Password : " + entity.getPassword());
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/login\">Click Here For Login</a>");
		emailUtil.sendMail(to, subject, body.toString());
		return true;
	}

}
