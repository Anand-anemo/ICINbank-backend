package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.UserHistoryRepository;
import com.onlinebankmanagement.icinbank.usersservices.model.UserHistory;
import com.onlinebankmanagement.icinbank.usersservices.service.UserHistoryService;

@Service
public class UserHistoryServiceImpl  implements UserHistoryService{
	
	@Autowired
	private UserHistoryRepository uhRepo;
	

	@Override
	public UserHistory addAction(long account, int amount, int balance, String action) {
		
        LocalDate today = LocalDate.now();
		
		UserHistory row=new UserHistory();
		row.setAccount(account);
		row.setAction(action);
		row.setAmount(amount);
		row.setDate(today);
		return uhRepo.save(row);
	}

	@Override
	public List<UserHistory> getHistory(long account) {
		return uhRepo.findByAccount(account);
	}

}
