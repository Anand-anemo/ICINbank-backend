package com.onlinebankmanagement.icinbank.usersservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankmanagement.icinbank.usersservices.model.ChequebookRequest;
import com.onlinebankmanagement.icinbank.usersservices.response.ChequeResponse;
import com.onlinebankmanagement.icinbank.usersservices.service.ChequebookService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChequeBookController {
	
	@Autowired
	private ChequebookService chequebookService;
	
	
	@PostMapping("/cheque/request")
	public ChequeResponse createrequest(@RequestBody ChequebookRequest chequebook) {
		return chequebookService.createrequest(chequebook);
	}

	@GetMapping("/cheque/getbyAccount/{account}")
	public List<ChequebookRequest> getRequests(@PathVariable("account") long account) {
		List<ChequebookRequest> list=chequebookService.getRequests(account);
		//Collections.reverse(list);
		//return service.getRequests(account);
		return list;
	}


}
