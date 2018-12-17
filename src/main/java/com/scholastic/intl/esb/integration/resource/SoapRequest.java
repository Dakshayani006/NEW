package com.scholastic.intl.esb.integration.resource;

public class SoapRequest implements Request {
	private static final long serialVersionUID = 5604738397233752154L;
	private String request;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

}
