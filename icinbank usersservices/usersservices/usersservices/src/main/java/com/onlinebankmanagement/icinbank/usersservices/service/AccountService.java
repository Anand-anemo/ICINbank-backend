package com.onlinebankmanagement.icinbank.usersservices.service;



import com.onlinebankmanagement.icinbank.usersservices.model.Account;
import com.onlinebankmanagement.icinbank.usersservices.response.DepositResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.TransferResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.WithdrawResponse;


public interface AccountService {
	public Account newAccount(String username,int userId);
	public Account getAccount(String username);
	public DepositResponse deposit(long acc,int amount);
	public WithdrawResponse withdraw(long acc,int amount);
	public TransferResponse transfer(long saccount,long raccount,int amount);
	public Account getAccountDetails(long account);
	public Account updateAccount(Account account);

}
