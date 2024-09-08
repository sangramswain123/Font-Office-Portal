package com.sangram.service;

import com.sangram.binding.ForgotPassword;
import com.sangram.binding.LoginForm;
import com.sangram.binding.SignupForm;
import com.sangram.binding.UnlockForm;

public interface UserService {
	
	public boolean login(LoginForm form);
	
	public boolean signup(SignupForm form);
	
	public String unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(ForgotPassword form);
}
