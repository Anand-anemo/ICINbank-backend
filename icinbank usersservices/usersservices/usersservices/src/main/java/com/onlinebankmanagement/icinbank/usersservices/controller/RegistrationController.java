package com.onlinebankmanagement.icinbank.usersservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.response.RegisterResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.RegistrationService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {
	
	@Autowired
	private RegistrationService registreService;

	@PostMapping("/register")
	public RegisterResponse createUser(@RequestBody User user) {

		return registreService.createAccount(user);
	}

	
	

}
