package com.onlinebankmanagement.icinbank.usersservices.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankmanagement.icinbank.usersservices.model.ChequebookRequest;

@Repository
public interface ChequeBookRepository extends CrudRepository<ChequebookRequest,Integer> {
	
	public List<ChequebookRequest> findByAccount(long account);

}
