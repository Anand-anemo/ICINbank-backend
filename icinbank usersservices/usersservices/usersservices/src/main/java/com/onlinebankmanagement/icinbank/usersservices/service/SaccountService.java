package com.onlinebankmanagement.icinbank.usersservices.service;

import com.onlinebankmanagement.icinbank.usersservices.model.Saccount;
import com.onlinebankmanagement.icinbank.usersservices.response.DepositResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.TransferResponse;
import com.onlinebankmanagement.icinbank.usersservices.response.WithdrawResponse;

public interface SaccountService {
	
	public Saccount getAccountDetails(long account);
	public Saccount getAccount(String username);
	public Saccount newAccount(String username,int userId);
	public DepositResponse deposit(long acc,int amount);
	public WithdrawResponse withdraw(long acc,int amount);
	public TransferResponse transfer(long saccount,long raccount,int amount);

}
