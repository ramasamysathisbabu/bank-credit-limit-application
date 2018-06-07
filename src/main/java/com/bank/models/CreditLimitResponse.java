package com.bank.models;

public class CreditLimitResponse {
	private String approvalStatus;
	private String newCreditLimitAmount;
	private String error;
	
	public String getNewCreditLimitAmount() {
		return newCreditLimitAmount;
	}
	public void setNewCreditLimitAmount(String newCreditLimitAmount) {
		this.newCreditLimitAmount = newCreditLimitAmount;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	@Override
	public String toString() {
		return "CustomerResponse [approvalStatus=" + approvalStatus + ", newCreditLimitAmount=" + newCreditLimitAmount
				+ ", error=" + error + "]";
	}

}
