package com.onlinebankmanagement.icinbank.usersservices.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankmanagement.icinbank.usersservices.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account,Integer> {
	
	public Account findByUsername(String username);
	public Account findByAccno(long accno);

}