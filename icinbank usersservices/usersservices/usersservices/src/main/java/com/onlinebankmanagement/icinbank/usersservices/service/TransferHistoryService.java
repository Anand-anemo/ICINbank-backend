package com.onlinebankmanagement.icinbank.usersservices.service;

import java.util.List;

import com.onlinebankmanagement.icinbank.usersservices.model.Transfer;

public interface TransferHistoryService {
	
	public Transfer addAction(long saccount, long raccount, int amount);
	public List<Transfer> getTransfers(long account);

}
