package com.onlinebankmanagement.icinbank.usersservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.usersservices.details.LoginDetails;
import com.onlinebankmanagement.icinbank.usersservices.response.LoginResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.Impl.LoginServiceImpl;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
	
	@Autowired
	LoginServiceImpl loginService;
	
	@PostMapping("/login")
	public LoginResponse userLogin(@RequestBody LoginDetails details) {
		
		return loginService.customerLogin(details);
		
	}

}
