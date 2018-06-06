package com.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entities.CreditLimitEligibilityResponse;
import com.bank.entities.CustomerRequest;
import com.bank.entities.CustomerResponse;
import com.bank.service.BankCreditLimitService;

@RestController
public class BankCreditLimitController {

	@Autowired
	BankCreditLimitService bankCreditLimitService = null;
	
	@RequestMapping(value = "/credit/increase", method = RequestMethod.POST)
	public ResponseEntity<CustomerResponse> requestCreditLimitIncrease(@RequestBody @Valid CustomerRequest customerRequest) {

		ResponseEntity<CustomerResponse> creditLimitEligibilityResponse = bankCreditLimitService.requestCreditLimitIncreaseEligibility(customerRequest.getSsn());
		System.out.println("Bank Controller:" + creditLimitEligibilityResponse);
		return creditLimitEligibilityResponse;
	}
	
}
