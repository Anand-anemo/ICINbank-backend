package com.onlinebankmanagement.icinbank.usersservices.service;

import com.onlinebankmanagement.icinbank.usersservices.details.LoginDetails;
import com.onlinebankmanagement.icinbank.usersservices.response.LoginResponse;

public interface LoginService {
	
	public LoginResponse customerLogin(LoginDetails details);

}
