package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.AccountRepository;
import com.onlinebankmanagement.icinbank.usersservices.dao.SaccountRepository;
import com.onlinebankmanagement.icinbank.usersservices.dao.UserRepository;
import com.onlinebankmanagement.icinbank.usersservices.model.Account;
import com.onlinebankmanagement.icinbank.usersservices.model.Saccount;
import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.response.DepositResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.TransferResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.WithdrawResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.SaccountService;
import com.onlinebankmanagement.icinbank.usersservices.service.TransferHistoryService;
import com.onlinebankmanagement.icinbank.usersservices.service.UserHistoryService;

@Service
public class SaccountServiceImpl implements SaccountService {
	
	@Autowired
	private SaccountRepository sAccRepo;
	
	@Autowired
	private UserHistoryService userHistoryService;
	
	@Autowired
	private TransferHistoryService transferHistoryService;
	
	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	private final String bankCode = "3914";
	private final String countryCode = "91";
	private final String branchCode = "820";
	private final String accountcode="2";

	@Override
	public Saccount getAccountDetails(long account) {
		
		return sAccRepo.findByAccno(account);
		
	}

	@Override
	public Saccount getAccount(String username) {
		return sAccRepo.findByUsername(username);
	}

	@Override
	public Saccount newAccount(String username, int userId) {
		Saccount account =new Saccount();
		account.setUsername(username);
		account.setAccno(generate_saving(userId));
		account.setUser(userRepo.findByUsername(username));
		return sAccRepo.save(account);
	}

	@Override
	public DepositResponse deposit(long acc, int amount) {
		
		DepositResponse response=new DepositResponse();
		boolean flag=true;
		try {
			Saccount account=sAccRepo.findByAccno(acc);
			account.setBalance(account.getBalance()+amount);
			userHistoryService.addAction(acc, amount, account.getBalance(), "deposit");
		    sAccRepo.save(account);
			response.setResponseMessage("Rs."+amount+" successfully deposited into your account balance is now Rs."+account.getBalance());
			response.setDepositStatus(flag);
		} catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setDepositStatus(flag);
		}
		response.setAccount(acc);
		return response; 
	}

	@Override
	public WithdrawResponse withdraw(long acc, int amount) {
		
		WithdrawResponse response=new WithdrawResponse();
		boolean flag=true;
		
		try {
			Saccount account=sAccRepo.findByAccno(acc);
			User user=userRepo.findByUsername(account.getUsername());
			if(user.getFeatureStatus()==2 || user.getFeatureStatus()==3)
			{
			
			if(account.getBalance()>=amount) 
				{
				account.setBalance(account.getBalance()-amount);
				userHistoryService.addAction(acc, amount, account.getBalance(), "withdraw");
				sAccRepo.save(account);
				response.setResponseMessage("Rs."+amount+" successfully withdrawn your account balance is now Rs."+account.getBalance());
				response.setWithdrawStatus(flag);
				}
			else 
				{
				flag=false;
				response.setResponseMessage("Insufficient funds to complete the transaction");
				response.setWithdrawStatus(flag);
				}
			}
			else {
				flag=false;
				response.setResponseMessage("This function is not available for your account");
				response.setWithdrawStatus(flag);
			}
		} catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setWithdrawStatus(flag);
		}
		response.setAccount(acc);
		return response;
	}

	@Override
	public TransferResponse transfer(long saccount, long raccount, int amount) {
		
		TransferResponse response=new TransferResponse();
		boolean flag=true;
		
		try {
			Saccount senderAccount=sAccRepo.findByAccno(saccount);
			if(isprimary(raccount)) {
				Account receiverAccount=accRepo.findByAccno(raccount);
				if(senderAccount.getAccno()!=receiverAccount.getAccno()) 
				{
				if(senderAccount.getBalance()>=amount) {
					User user=userRepo.findByUsername(senderAccount.getUsername());
					
					if(user.getFeatureStatus()==3) 
					{
						senderAccount.setBalance(senderAccount.getBalance()-amount);
						receiverAccount.setBalance(receiverAccount.getBalance()+amount);
						transferHistoryService.addAction(saccount, raccount, amount);
						sAccRepo.save(senderAccount);
						accRepo.save(receiverAccount);
						response.setResponseMessage("Rs."+amount+" successfully transferred to account "+receiverAccount.getAccno());
						response.setTransferStatus(flag);
					}
					else {
						flag=false;
						response.setResponseMessage("This feature is not available for your account");
						response.setTransferStatus(flag);
					}
				}
				else 
					{
					flag=false;
					response.setResponseMessage("Insufficient funds to complete the transfer");
					response.setTransferStatus(flag);
					}
			}
			else {
				flag=false;
				response.setResponseMessage("sender and recieiver accounts are same");
				response.setTransferStatus(flag);
			}
			}
			else
			{
				Saccount receiverAccount=sAccRepo.findByAccno(raccount);
				if(senderAccount.getAccno()!=receiverAccount.getAccno()) 
				{
					
					if(senderAccount.getBalance()>amount) {
						User user=userRepo.findByUsername(senderAccount.getUsername());
						
						if(user.getFeatureStatus()==3) 
							{
								senderAccount.setBalance(senderAccount.getBalance()-amount);
								receiverAccount.setBalance(receiverAccount.getBalance()+amount);
								transferHistoryService.addAction(saccount, raccount, amount);
								sAccRepo.save(senderAccount);
								sAccRepo.save(receiverAccount);
								response.setResponseMessage("Rs."+amount+" successfully transferred to account "+receiverAccount.getAccno());
								response.setTransferStatus(flag);
							}
						else {
								flag=false;
								response.setResponseMessage("This function isnt available for the account");
								response.setTransferStatus(flag);
							}
						}
					
					else {
							flag=false;
							response.setResponseMessage("Insufficient funds to complete the transfer");
							response.setTransferStatus(flag);
						}
				}
				
				else {
						flag=false;
						response.setResponseMessage("sender and recieiver accounts are same");
						response.setTransferStatus(flag);
				}
			}
		} catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setTransferStatus(flag);
		}
		response.setSaccount(saccount);
		return response;
	}
	
	public long generate_saving(int userId) {
		String accNo = bankCode+countryCode+branchCode+accountcode+String.valueOf(userId);
		return Long.parseLong(accNo);
	}
	
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


}
