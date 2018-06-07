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

import com.bank.models.CreditLimitEligibilityRequest;
import com.bank.models.CreditLimitEligibilityResponse;
import com.bank.models.CreditLimitResponse;

@Service
public class BankCreditLimitService {

	private final String X_REQUEST_ID = "X-Request-ID";
	private final String APPROVED = "Approved";
	private final String REJECTED = "Rejected";
	private final String SUCCESS_MESSAGE = "Your new credit limit is ";
	private final String CREDIT_AGENCY_APPROVAL_STATUS = "yes";

	@Autowired
	RestTemplate restTemplate;

	@Value("${credit.agency.service.url}")
	private String creditAgencyServiceUrl;

	@Value("${credit.agency.authorization.token.value}")
	private String creditAgencyAuthorizationTokenValue;

	public ResponseEntity<CreditLimitResponse> requestCreditLimitIncrease(String ssn) {
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
			CreditLimitResponse creditLimitResponse = new CreditLimitResponse();
			creditLimitResponse.setError("Credit decisioning system down");
			return new ResponseEntity<CreditLimitResponse>(creditLimitResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return transformServiceResponse(responseEntity);
	}

	public ResponseEntity<CreditLimitResponse> transformServiceResponse(
			ResponseEntity<CreditLimitEligibilityResponse> responseEntity) {

		CreditLimitResponse creditLimitResponse = new CreditLimitResponse();
		HttpHeaders responseHeaders = new HttpHeaders();

		Optional<ResponseEntity<CreditLimitEligibilityResponse>> respEntity = Optional.of(responseEntity);
		
		if (respEntity.isPresent() && HttpStatus.OK.equals(respEntity.get().getStatusCode())) {
			responseHeaders.set(X_REQUEST_ID, respEntity.get().getHeaders().getFirst(X_REQUEST_ID));
			CreditLimitEligibilityResponse serviceResponseContent = respEntity.get().getBody();
			creditLimitResponse.setApprovalStatus(CREDIT_AGENCY_APPROVAL_STATUS.equalsIgnoreCase(serviceResponseContent.getEligibilityStatus()) ? APPROVED : REJECTED);
			String creditLimitAmount = serviceResponseContent.getNewCreditLimitAmount();
			creditLimitResponse.setNewCreditLimitAmount(CREDIT_AGENCY_APPROVAL_STATUS.equalsIgnoreCase(serviceResponseContent.getEligibilityStatus()) ? SUCCESS_MESSAGE + creditLimitAmount : creditLimitAmount);
			creditLimitResponse.setError(serviceResponseContent.getError());
		} else {
			creditLimitResponse.setError("Sorry, our application is down at present. Please try again later");
			responseHeaders.set(X_REQUEST_ID, respEntity.get().getHeaders().getFirst(X_REQUEST_ID));
		}
		
		return new ResponseEntity<CreditLimitResponse>(creditLimitResponse, responseHeaders, HttpStatus.OK);
	}

}
