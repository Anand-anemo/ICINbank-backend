package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.UserDisplayRepository;
import com.onlinebankmanagement.icinbank.usersservices.dao.UserRepository;
import com.onlinebankmanagement.icinbank.usersservices.details.UpdateDetails;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.model.UserDisplay;
import com.onlinebankmanagement.icinbank.usersservices.response.UpdateResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserDisplayRepository userDisplayRepo;


	@Override
	public UpdateResponse updateUser(UpdateDetails user) {
		
		boolean flag=true;
		UpdateResponse response=new UpdateResponse();
		String message="Update successful"; 
		try {
			int counter = 0;
			User u=userRepo.findByUsername(user.getUsername());
			if(user.getAddress().length()!=0) {
				counter++;
				u.setAddress(user.getAddress());
			}
			if(user.getPassword().length()!=0 && user.getNewpassword().length()!=0) {
				counter++;
				String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
				u.setPassword(hashedPassword);
			}
			if(user.getEmail().length()!=0) {
				counter++;
				u.setEmail(user.getEmail());
			}
			if(user.getPhone()!=0) {
				counter++;
				u.setPhone(user.getPhone());
			}
			System.out.println(counter);
			if(counter>0) {
				userRepo.save(u);
			}
			else {
				flag=false;
				message="Please enter some information to update";
			}
		}catch(Exception e){
			flag=false;
			response.setMessage("Update unsuccesful");
		}
		response.setMessage(message);
		response.setFlag(flag);
		return response;
		
	}

	@Override
	public User getUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public UserDisplay userDisplay(String username) {
		UserDisplay user=userDisplayRepo.getCurrentUser(username);
		return user;
	}

}
