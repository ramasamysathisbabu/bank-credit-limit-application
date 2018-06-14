package com.bank.util;

import java.io.IOException;

import com.bank.models.CreditLimitRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return objectMapper.writeValueAsBytes(object);
	}

	public static byte[] createCreditLimitIncreaseHappyPathData() throws IOException {
		CreditLimitRequest request = new CreditLimitRequest();
		request.setName("TestName");
		request.setSsn("123456789");
		request.setCreditCardNumber("1111222233334444");
		request.setZipCode("43235");
		return convertObjectToJsonBytes(request);
	}
	
	public static byte[] createCreditLimitIncreaseBadRequestData() throws IOException {
		CreditLimitRequest request = new CreditLimitRequest();
		request.setName("TestName");
		request.setSsn("12345678");
		request.setCreditCardNumber("1111222233334444");
		request.setZipCode("43235");
		return convertObjectToJsonBytes(request);
	}
}
