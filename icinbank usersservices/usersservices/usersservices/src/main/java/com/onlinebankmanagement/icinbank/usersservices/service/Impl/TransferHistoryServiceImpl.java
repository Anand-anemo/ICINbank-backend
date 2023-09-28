package com.onlinebankmanagement.icinbank.usersservices.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankmanagement.icinbank.usersservices.dao.TransferHistoryRepository;
import com.onlinebankmanagement.icinbank.usersservices.model.Transfer;
import com.onlinebankmanagement.icinbank.usersservices.service.TransferHistoryService;

@Service
public class TransferHistoryServiceImpl implements TransferHistoryService {
	
	@Autowired
	private TransferHistoryRepository transferHistoryRepo;

	@Override
	public Transfer addAction(long saccount, long raccount, int amount) {
		
		LocalDate today = LocalDate.now();
		Transfer transfer=new Transfer();
		transfer.setSaccount(saccount);
		transfer.setRaccount(raccount);
		transfer.setAmount(amount);
		transfer.setDate(today);
		return transferHistoryRepo.save(transfer);
		
	}

	@Override
	public List<Transfer> getTransfers(long account) {
		
		List<Transfer> sender=transferHistoryRepo.findBySaccount(account);
		List<Transfer> receiver=transferHistoryRepo.findByRaccount(account);
		List<Transfer> merged=new ArrayList<>();
		merged.addAll(sender);
		merged.addAll(receiver);
		Collections.sort(merged);
		return merged;
	}

}
