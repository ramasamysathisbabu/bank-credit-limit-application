package com.bank.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.entities.CreditLimitEligibilityRequest;
import com.bank.entities.CreditLimitEligibilityResponse;
import com.bank.entities.CustomerResponse;

@Service
public class BankCreditLimitService {

	private final String X_REQUEST_ID = "X-Request-ID";

	@Autowired
	RestTemplate restTemplate;

	@Value("${credit.agency.service.url}")
	private String creditAgencyServiceUrl;

	@Value("${credit.agency.authorization.token.value}")
	private String creditAgencyAuthorizationTokenValue;

	public ResponseEntity<CustomerResponse> requestCreditLimitIncreaseEligibility(String ssn) {
		ResponseEntity<CreditLimitEligibilityResponse> responseEntity = null;
		CreditLimitEligibilityRequest request = new CreditLimitEligibilityRequest();
		request.setSsn(ssn);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, creditAgencyAuthorizationTokenValue);
		headers.set(X_REQUEST_ID, UUID.randomUUID().toString());

		HttpEntity<CreditLimitEligibilityRequest> entity = new HttpEntity<CreditLimitEligibilityRequest>(request,
				headers);
		try {
			responseEntity = restTemplate.exchange(creditAgencyServiceUrl, HttpMethod.POST, entity,
					CreditLimitEligibilityResponse.class);
		} catch (Exception e) {
			CustomerResponse customerResponse = new CustomerResponse();
			customerResponse.setError("Credit decisioning system down");
			return new ResponseEntity<CustomerResponse>(customerResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return transformServiceResponse(responseEntity);
	}

	public ResponseEntity<CustomerResponse> transformServiceResponse(
			ResponseEntity<CreditLimitEligibilityResponse> responseEntity) {

		CustomerResponse customerResponse = new CustomerResponse();
		HttpHeaders responseHeaders = new HttpHeaders();

		Optional<ResponseEntity<CreditLimitEligibilityResponse>> respEntity = Optional.of(responseEntity);
		
		if (respEntity.isPresent() && HttpStatus.OK.equals(respEntity.get().getStatusCode())) {
			responseHeaders.set(X_REQUEST_ID, respEntity.get().getHeaders().getFirst(X_REQUEST_ID));
			CreditLimitEligibilityResponse serviceResponseContent = respEntity.get().getBody();
			customerResponse.setEligibilityStatus(serviceResponseContent.getEligibilityStatus());
			customerResponse.setNewCreditLimitAmount(serviceResponseContent.getNewCreditLimitAmount());
			customerResponse.setError(serviceResponseContent.getError());
		} else {
			customerResponse.setError("Sorry, our application is down at present. Please try again later");
			responseHeaders.set(X_REQUEST_ID, respEntity.get().getHeaders().getFirst(X_REQUEST_ID));
		}
		
		return new ResponseEntity<CustomerResponse>(customerResponse, responseHeaders, HttpStatus.OK);
	}

}
