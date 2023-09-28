package com.onlinebankmanagement.icinbank.adminservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.adminservices.model.ChequebookRequest;
import com.onlinebankmanagement.icinbank.adminservices.model.User;
import com.onlinebankmanagement.icinbank.adminservices.model.UserDisplay;
import com.onlinebankmanagement.icinbank.adminservices.service.impl.AdminServiceImpl;
import com.onlinebankmanagement.icinbank.adminservices.service.impl.MailServiceImpl;

@RestController
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminServiceImpl adminService;
	
	@Autowired
	private MailServiceImpl mailService;
	
	
	@GetMapping("user/{username}/features/{featureId}")
	public void setUserFeatures(@PathVariable("username") String username, @PathVariable("featureId") int featureId) {
		adminService.setUserFeatures(username, featureId);
	}
	
	@GetMapping("user/{username}/authorize")
	public void authorizeUser(@PathVariable("username") String username) {
		
		adminService.authorizeUser(username);
//		try {
//			adminService.authorizeUser(username);
//			mailService.sendAuthorizedEmail(username);
//		} catch (EmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@GetMapping("user/{username}/authorize/cancel")
	public void  cancelAuthorization(@PathVariable("username") String username) {
//		try {
//			mailService.sendAuthorizeCancelEmail(username);
//		} catch (EmailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		adminService.cancelAuthorization(username);
	}
	
	@GetMapping("user/unauthorized/all")
	public List<User> getAllUnauthorizedUsers()
	{
		return adminService.getAllUnauthorizedUsers();
	}
	
	@GetMapping("/user/all")
	public List<UserDisplay> getAllUsers()
	{
		return adminService.getAllUsers();
	}
	
//	@GetMapping("/user/transfers/{account}")
//	public List<Transfer> getTransactionList(@PathVariable("account") int account)
//	{
//		return service.getAllTransactions(account);
//	}
	
	@GetMapping("/chequebook/request/all")
	public List<ChequebookRequest> getAllChequeBookRequests()
	{
		return adminService.getAllChequebookRequests();
	}
	
	@GetMapping("/user/{accNo}/chequebook/request/confirm")
	public void confirmChequeBookRequest(@PathVariable("accNo") long accNo)
	{
		adminService.acceptChequebookRequest(accNo);
	}
	
	@GetMapping("/user/{username}/enable")
	public void enableUser(@PathVariable("username") String username)
	{
		adminService.enableUser(username);
	}
	
	@GetMapping("/user/{username}/disable")
	public void disableUser(@PathVariable("username") String username)
	{
		adminService.disableUser(username);
	}
	
	@GetMapping("search/user/{userDetail}")
	public UserDisplay searchUser(@PathVariable("userDetail") String userDetail) {
		return adminService.searchUser(userDetail);
	}

}
