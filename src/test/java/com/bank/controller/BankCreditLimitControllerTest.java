package com.bank.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.bank.models.CreditLimitResponse;
import com.bank.service.BankCreditLimitService;
import com.bank.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class BankCreditLimitControllerTest {

	MockMvc mockMvc;

	@Mock
	RestTemplate restTemplate;

	@Mock
	BankCreditLimitService bankCreditLimitService;

	@InjectMocks
	BankCreditLimitController bankCreditLimitController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(bankCreditLimitController).build();
	}

	@Test
	public void testRequestCreditLimitIncrease() throws Exception {
		CreditLimitResponse creditLimitResponse = new CreditLimitResponse();
		creditLimitResponse.setApprovalStatus("yes");
		creditLimitResponse.setNewCreditLimitAmount("12000");

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer PNC-Auth-12345");
		headers.set("X-Request-ID", UUID.randomUUID().toString());

		ResponseEntity<CreditLimitResponse> response = new ResponseEntity<CreditLimitResponse>(creditLimitResponse,
				headers, HttpStatus.OK);

		when(bankCreditLimitService.requestCreditLimitIncrease(anyString())).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.put("/creditcard/limits").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.createCreditLimitIncreaseHappyPathData()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.approvalStatus", Matchers.is("yes")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.newCreditLimitAmount", Matchers.is("12000")));
	}
}
