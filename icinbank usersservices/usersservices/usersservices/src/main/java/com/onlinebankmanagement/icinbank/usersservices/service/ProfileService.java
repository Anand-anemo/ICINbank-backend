package com.onlinebankmanagement.icinbank.usersservices.service;

import com.onlinebankmanagement.icinbank.usersservices.details.UpdateDetails;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.model.UserDisplay;
import com.onlinebankmanagement.icinbank.usersservices.response.UpdateResponse;

public interface ProfileService {
	
	public UpdateResponse updateUser(UpdateDetails user);
	public User getUser(String username);
	public UserDisplay userDisplay(String username);

}
