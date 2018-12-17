package com.scholastic.intl.esb.integration.resource;

public class UpdateChildRequestWrapper implements Request{

	private static final long serialVersionUID = 1L;
	private UpdateChildCustToNSRequest request;
	public UpdateChildCustToNSRequest getRequest() {
		return request;
	}
	public void setRequest(UpdateChildCustToNSRequest request) {
		this.request = request;
	}
}
