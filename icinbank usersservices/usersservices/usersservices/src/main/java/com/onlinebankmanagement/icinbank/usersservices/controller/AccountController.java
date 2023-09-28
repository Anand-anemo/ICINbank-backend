package com.onlinebankmanagement.icinbank.usersservices.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.usersservices.dao.AccountRepository;
import com.onlinebankmanagement.icinbank.usersservices.dao.SaccountRepository;
import com.onlinebankmanagement.icinbank.usersservices.details.TransactionDetails;
import com.onlinebankmanagement.icinbank.usersservices.details.TransferDetails;
import com.onlinebankmanagement.icinbank.usersservices.model.Account;
import com.onlinebankmanagement.icinbank.usersservices.model.Saccount;
import com.onlinebankmanagement.icinbank.usersservices.model.Transfer;
import com.onlinebankmanagement.icinbank.usersservices.model.UserHistory;
import com.onlinebankmanagement.icinbank.usersservices.response.DepositResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.TransferResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.WithdrawResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.AccountService;
import com.onlinebankmanagement.icinbank.usersservices.service.SaccountService;
import com.onlinebankmanagement.icinbank.usersservices.service.TransferHistoryService;
import com.onlinebankmanagement.icinbank.usersservices.service.UserHistoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
	
	@Autowired
	private AccountService accService; //service
	
	@Autowired 
	private SaccountService sAccService;//sservice;
	
	@Autowired
	private UserHistoryService userHistoryService;//hservice;
	
	@Autowired
	private TransferHistoryService transferHistoryService;//tservice
	
	@Autowired
	private AccountRepository accRepo;//adao
	
	@Autowired
	private SaccountRepository sAccRepo;//sdao
	
	private final String ifsc="ICIN0012376548";
	
	
	public static boolean isprimary(long account) {
		String s = Long.toString(account).substring(0, 10);
		String check="3914918201";
		if(s.equals(check)) {
			return true;
		}
		else 
		{
			return false;
		}
		
	}
	
	
	@GetMapping("/account/details/{account}")
	public Account getAccountDetails(@PathVariable("account") int account ) {
		
		return accService.getAccountDetails(account);
		
	}
	
	@PutMapping("/account/profile")
	public Account updateProfile(@RequestBody Account account) {
		return accService.updateAccount(account);
	}
	
	@GetMapping("/account/getprimary/{username}")
	public Account getPrimarydetails(@PathVariable("username") String username) {
		return accService.getAccount(username);
	}
	
	@GetMapping("/account/getsaving/{username}")
	public Saccount getSavingdetails(@PathVariable("username") String username) {
		return sAccService.getAccount(username);
	}
	
	@PostMapping("/account/deposit")
	public DepositResponse deposit(@RequestBody TransactionDetails details) {
		//adao.findByUsername(adao.findByAccno(details.getAccount()).getUsername());
		if(isprimary(details.getAccount())) {
			return accService.deposit(details.getAccount(), details.getAmount());
		}
		else {
			return sAccService.deposit(details.getAccount(), details.getAmount());
		}
	}
	
	@PostMapping("/account/withdraw")
	public WithdrawResponse withdraw(@RequestBody TransactionDetails details) {
		
		if(isprimary(details.getAccount())) {
		return accService.withdraw(details.getAccount(), details.getAmount());
		}
		else {
			return sAccService.withdraw(details.getAccount(), details.getAmount());
		}
	}
	
	@PostMapping("/account/transfer")
	public TransferResponse transfer(@RequestBody TransferDetails details) {
		try {
			if(details.getIfsc().equals(ifsc)) 
			{
						Account p=accRepo.findByUsername(details.getUsername());
						Saccount s=sAccRepo.findByUsername(details.getUsername());
						
						if(p.getAccno()==details.getSaccount() || s.getAccno()==details.getSaccount()) {
						//String len = Integer.toString(details.getSaccount());
						if(isprimary(details.getSaccount())) {
						return accService.transfer(details.getSaccount(), details.getRaccount(), details.getAmount());
						}
						else
						{
							return sAccService.transfer(details.getSaccount(), details.getRaccount(), details.getAmount());
						}
						}
						else {
							TransferResponse response=new TransferResponse();
							response.setSaccount(details.getSaccount());
							response.setResponseMessage("Dear user You can only transfer funds from the accounts registed with you");
							response.setTransferStatus(false);
							return response;
			}
			}
			else {
				TransferResponse response=new TransferResponse();
						response.setSaccount(details.getSaccount());
						response.setResponseMessage("IFSC code is incorrect");
						response.setTransferStatus(false);
						return response;
			}
		} catch (Exception e) {
			TransferResponse response=new TransferResponse();
			response.setSaccount(details.getSaccount());
			response.setResponseMessage("Please provide an IFSC code");
			response.setTransferStatus(false);
			return response;
			
		}
	}
	
	@GetMapping("/account/getHistory/{account}")
	public List<UserHistory> getHistory(@PathVariable("account") long account )
	{
		List<UserHistory> history=userHistoryService.getHistory(account);
		Collections.reverse(history);
		return history;
	}
	
	@GetMapping("/account/getTransfers/{account}")
	public List<Transfer> getTransfers(@PathVariable("account") long account )
	{
		return transferHistoryService.getTransfers(account);
	}



}
