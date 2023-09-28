package com.onlinebankmanagement.icinbank.usersservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onlinebankmanagement.icinbank.usersservices.model.User;
import com.onlinebankmanagement.icinbank.usersservices.model.UserDisplay;

@Repository
public interface UserDisplayRepository extends JpaRepository<User,Integer> {
	@Query("SELECT new com.onlinebankmanagement.icinbank.usersservices.model.UserDisplay(u.username,a.accno,a.balance,s.accno,s.balance)" + "FROM User u ,Account a,Saccount s WHERE u.username=?1 and u.username=a.username and u.username=s.username")
	public UserDisplay getCurrentUser(String username);

}
