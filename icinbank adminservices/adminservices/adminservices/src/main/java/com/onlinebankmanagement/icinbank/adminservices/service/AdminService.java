package com.onlinebankmanagement.icinbank.adminservices.service;

import java.util.List;

import com.onlinebankmanagement.icinbank.adminservices.model.ChequebookRequest;
import com.onlinebankmanagement.icinbank.adminservices.model.Transfer;
import com.onlinebankmanagement.icinbank.adminservices.model.User;
import com.onlinebankmanagement.icinbank.adminservices.model.UserDisplay;

public interface AdminService {
	
	public List<UserDisplay> getAllUsers();

	public List<Transfer> getAllTransactions(long accountNo);

	public List<ChequebookRequest> getAllChequebookRequests();

	// public void acceptChequebookRequest(int accNo);
	public void enableUser(String username);

	public void disableUser(String username);

	public void authorizeUser(String username);

	public void cancelAuthorization(String username);

	public List<User> getAllUnauthorizedUsers();

	public void setUserFeatures(String username, int featureId);

	public UserDisplay searchUser(String userDetail);

	void acceptChequebookRequest(long accNo);


}
