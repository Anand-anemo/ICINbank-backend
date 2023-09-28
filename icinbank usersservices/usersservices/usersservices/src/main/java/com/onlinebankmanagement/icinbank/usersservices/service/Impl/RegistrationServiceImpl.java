package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.UserRepository;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.response.RegisterResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.AccountService;
import com.onlinebankmanagement.icinbank.usersservices.service.RegistrationService;
import com.onlinebankmanagement.icinbank.usersservices.service.SaccountService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AccountService accService;
	
	@Autowired 
	private SaccountService sAccService;

	@Override
	public RegisterResponse createAccount(User details) {
		
		RegisterResponse response = new RegisterResponse();
		boolean flag = true;
		String message = "Registration Succesful";
		
		if(EmailAlreadyExists(details.getEmail())) {
			message = "Email already Exists";
			flag = false;
		}
		
		if(PhoneAlreadyExists(details.getPhone())) {
			message = "Phone number already Exists";
			flag = false;
		}
		
		if(usernameAlreadyExists(details.getUsername())) {
			message = "Username already Exists";
			flag = false;
		}
		
		if(flag) {
			String hashedPassword = DigestUtils.sha256Hex(details.getPassword());
			details.setPassword(hashedPassword);
			userRepo.save(details);
//			User u=dao.findByUsername(user.getUsername());
//			int id=u.getId();
//			service.newAccount(user.getUsername(),id);
//			sservice.newAccount(user.getUsername(),id);
		}
		response.setRegistrationStatus(flag);
		response.setResponseMessage(message);
		response.setUsername(details.getUsername());
		return response;
		
		
	}

	@Override
	public boolean usernameAlreadyExists(String username) {
		try {
			User u=userRepo.findByUsername(username);
			System.out.println(u.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean EmailAlreadyExists(String email) {
		try {
			User u=userRepo.findByEmail(email);
			System.out.println(u.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean PhoneAlreadyExists(long l) {
		try {
			User u=userRepo.findByPhone(l);
			System.out.println(u.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
