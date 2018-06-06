package com.bank.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerRequest {
	
	@NotBlank(message = "Please provide name. Name cannot be empty")
	@NotEmpty(message = "Please provide name. Name cannot be empty")
	@NotNull(message = "Please provide name. Name cannot be null")
	private String name;
	
	@NotBlank(message = "Please provide ssn. SSN cannot be empty")
	@NotEmpty(message = "Please provide ssn. SSN cannot be empty")
	@Size(min=9, max=9, message = "SSN should be 9 digits")
	private String ssn;
	
	@NotBlank(message = "Please provide credit card number. Credit card number cannot be empty")
	@NotEmpty(message = "Please provide credit card number. Credit card number cannot be empty")
	@NotNull(message = "Please provide credit card number. Credit card number cannot be null")
	@Pattern(regexp="(^[0-9]{16})", message = "Creditcard number should be 16 digits")
	private String creditCardNumber;

	@NotBlank(message = "Please provide Zipcode. Zipcode cannot be empty")
	@NotEmpty(message = "Please provide Zipcode. Zipcode cannot cannot be empty")
	@NotNull(message = "Please provide Zipcode. Zipcode cannot cannot be null")	
	@Pattern(regexp="(^[0-9]{5})", message = "Zipcode should be 5 digits")
	private String zipCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", ssn=" + ssn + ", creditCardNumber=" + creditCardNumber + ", zipCode="
				+ zipCode + "]";
	}

}
