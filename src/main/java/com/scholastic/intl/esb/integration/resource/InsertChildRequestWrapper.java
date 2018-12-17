package com.scholastic.intl.esb.integration.resource;

public class InsertChildRequestWrapper implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InsertChildCustToNSRequest request;
	public InsertChildCustToNSRequest getRequest() {
		return request;
	}
	public void setRequest(InsertChildCustToNSRequest request) {
		this.request = request;
	}
}
