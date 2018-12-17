package com.scholastic.intl.esb.integration.resource;

public class FailedRequest implements Request {
	
	private String request;
	private int resubmissionCount;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getResubmissionCount() {
		return resubmissionCount;
	}

	public void setResubmissionCount(int resubmissionCount) {
		this.resubmissionCount = resubmissionCount;
	}
	
}