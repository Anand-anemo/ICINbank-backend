package com.onlinebankmanagement.icinbank.usersservices.service;

import java.util.List;

import com.onlinebankmanagement.icinbank.usersservices.model.ChequebookRequest;
import com.onlinebankmanagement.icinbank.usersservices.response.ChequeResponse;

public interface ChequebookService {
	
	public ChequeResponse createrequest(ChequebookRequest chequebook);
	public List<ChequebookRequest> getRequests(long account);

}
