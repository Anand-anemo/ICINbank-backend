package com.onlinebankmanagement.icinbank.usersservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.usersservices.details.UpdateDetails;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.model.UserDisplay;
import com.onlinebankmanagement.icinbank.usersservices.response.UpdateResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.ProfileService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@PutMapping("/profile/update")
	public UpdateResponse updateUser(@RequestBody UpdateDetails user) {
		// TODO Auto-generated method stub
		return profileService.updateUser(user);
	}

	@GetMapping("/profile/{username}")
	public User getUser(@PathVariable("username") String username) {
		// TODO Auto-generated method stub
		return profileService.getUser(username);
	}
	
	@GetMapping("home/{username}")
	public UserDisplay userDisplay(@PathVariable("username")String username) {
		return profileService.userDisplay(username);
	}

}
