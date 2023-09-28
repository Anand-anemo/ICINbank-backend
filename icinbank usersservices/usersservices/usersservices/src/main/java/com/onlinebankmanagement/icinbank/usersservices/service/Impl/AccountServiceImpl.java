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
import com.onlinebankmanagement.icinbank.usersservices.service.AccountService;
import com.onlinebankmanagement.icinbank.usersservices.service.TransferHistoryService;
import com.onlinebankmanagement.icinbank.usersservices.service.UserHistoryService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private UserHistoryService userHistoryService;
	
	@Autowired
	private TransferHistoryService transferHistoryService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SaccountRepository sAccRepo;
	
	private final String bankCode = "3914";
	private final String countryCode = "91";
	private final String branchCode = "820";
	private final String accountcode="1";
	
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

//------------------new Account-------------------
	@Override
	public Account newAccount(String username, int userId) {
		Account account=new Account();
		account.setUsername(username);
		account.setAccno(generate_saving(userId));
		account.setUser(userRepo.findByUsername(username));
		return accRepo.save(account);
	}
//----------------------getAccount--------------------
	@Override
	public Account getAccount(String username) {
		return accRepo.findByUsername(username);
	}
//------------------------deposit response--------------------
	@Override
	public DepositResponse deposit(long acc, int amount) {
DepositResponse response=new DepositResponse();
		
		boolean flag=true;
		try {
			Account account=accRepo.findByAccno(acc);
			account.setBalance(account.getBalance()+amount);
			userHistoryService.addAction(acc, amount, account.getBalance(), "credit");
			accRepo.save(account);
			response.setResponseMessage("Rs."+amount+" successfully deposited into your account balance is now Rs."+account.getBalance());
			response.setDepositStatus(flag);
		} 
		catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setDepositStatus(flag);
		}
		response.setAccount(acc);
		return response; 
	}
//---------------------------------withdrawResponse------------------------------
	@Override
	public WithdrawResponse withdraw(long acc, int amount) {
		WithdrawResponse response=new WithdrawResponse();
		boolean flag=true;
		try {
			Account account=accRepo.findByAccno(acc);
			User user=userRepo.findByUsername(account.getUsername());
			if(user.getFeatureStatus()==2 || user.getFeatureStatus()==3)
			{
			if(account.getBalance()>=amount) 
				{
					account.setBalance(account.getBalance()-amount);
					userHistoryService.addAction(acc, amount, account.getBalance(), "debit");
					accRepo.save(account);
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
			
		} 
		
		catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setWithdrawStatus(flag);
		}
		
		response.setAccount(acc);
		return response;
	}
//-----------------------------transferResponse------------------------------
	@Override
	public TransferResponse transfer(long saccount, long raccount, int amount) {
		
		TransferResponse response=new TransferResponse();
		boolean flag=true;
		
		try {
			Account senderAccount=accRepo.findByAccno(saccount);
			//String check = Long.toString(raccount);
			if(isprimary(raccount))
			{
				Account receiverAccount=accRepo.findByAccno(raccount);
				if(senderAccount.getAccno()!=receiverAccount.getAccno()) 
				{
					if(senderAccount.getBalance()>amount) {
						User user=userRepo.findByUsername(senderAccount.getUsername());
						
						if(user.getFeatureStatus()==3) 
						{
						senderAccount.setBalance(senderAccount.getBalance()-amount);
						receiverAccount.setBalance(receiverAccount.getBalance()+amount);
						transferHistoryService.addAction(saccount, raccount, amount);
						accRepo.save(senderAccount);
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
			else {
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
						accRepo.save(senderAccount);
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
		} 
		
		catch (Exception e) {
			flag=false;
			response.setResponseMessage("Account number is incorrect");
			response.setTransferStatus(flag);
		}
		response.setSaccount(saccount);
		return response;
	}
//------------------AccountDetails-----------------------------
	@Override
	public Account getAccountDetails(long account) {
		return accRepo.findByAccno(account);
	}
//---------------------updateAccount-----------------------
	@Override
	public Account updateAccount(Account account) {
		return accRepo.save(account);
	}

}
