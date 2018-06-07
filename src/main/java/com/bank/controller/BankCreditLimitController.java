package com.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.models.CreditLimitRequest;
import com.bank.models.CreditLimitResponse;
import com.bank.service.BankCreditLimitService;

@RestController
@RequestMapping("/creditcard")
public class BankCreditLimitController {

	@Autowired
	BankCreditLimitService bankCreditLimitService;
	
	@PutMapping(value = "/limits")
	public ResponseEntity<CreditLimitResponse> requestCreditLimitIncrease(@RequestBody @Valid CreditLimitRequest creditLimitRequest) {
		ResponseEntity<CreditLimitResponse> creditLimitEligibilityResponse = bankCreditLimitService.requestCreditLimitIncrease(creditLimitRequest.getSsn());
		return creditLimitEligibilityResponse;
	}
	
}
