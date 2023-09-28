package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.UserRepository;
import com.onlinebankmanagement.icinbank.usersservices.details.LoginDetails;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.response.LoginResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public LoginResponse customerLogin(LoginDetails details) {
		
		LoginResponse response = new LoginResponse();
		boolean flag = true;
		String message = "Login succesfull";
		User user = null;
		String hashedPassword = DigestUtils.sha256Hex(details.getPassword());
		try {
			user=userRepo.findByUsername(details.getUsername());
			if(user.getStatus()) {
				flag = false;
				message = "Dear Mr/Ms."+user.getFname()+" your account has been blocked for security reasons.";
			}
			if(!user.getAuthorizationStatus()) {
				flag = false;
				message = "Dear Mr/Ms."+user.getFname()+" your account has not been activated yet";
			}
			if(!hashedPassword.equals(user.getPassword())) {
				flag = false;
				message = "Username or password is incorrect";
			}
		} 
		catch (Exception e) {
			flag = false;
			message = "Username or password is incorrect";
		}
		
		response.setLoginStatus(flag);
		response.setResponseMessage(message);
		try {
			response.setUsername(user.getUsername());
		} catch (Exception e) {
			response.setUsername(details.getUsername());
		}
		return response;
		
	}

}
