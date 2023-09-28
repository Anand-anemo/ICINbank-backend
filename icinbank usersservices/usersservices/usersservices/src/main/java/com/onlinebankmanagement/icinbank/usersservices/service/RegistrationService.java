package com.onlinebankmanagement.icinbank.usersservices.service;

import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.response.RegisterResponse;

public interface RegistrationService {
	
	public RegisterResponse createAccount(User details); 
	public boolean usernameAlreadyExists(String username);
	public boolean EmailAlreadyExists(String email);
	public boolean PhoneAlreadyExists(long l);

}
