package com.onlinebankmanagement.icinbank.adminservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebankmanagement.icinbank.adminservices.model.Saccount;

@Repository
public interface SaccountRepository extends JpaRepository<Saccount, Integer> {
	
	public Saccount findByAccno(long accNo);

}
