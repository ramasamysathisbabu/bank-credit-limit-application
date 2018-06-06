package com.bank.entities;

public class ErrorDetails {
	  private String field;
	  private String error;
	  
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ErrorDetails(String field, String error) {
		super();
		this.field = field;
		this.error = error;
	}
}
