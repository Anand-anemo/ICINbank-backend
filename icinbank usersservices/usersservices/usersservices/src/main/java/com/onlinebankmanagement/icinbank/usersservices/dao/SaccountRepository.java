package com.onlinebankmanagement.icinbank.usersservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankmanagement.icinbank.usersservices.model.Saccount;

@Repository
public interface SaccountRepository extends JpaRepository<Saccount,Integer> {
	
	public Saccount findByUsername(String username);
	public Saccount findByAccno(long accno);

}
