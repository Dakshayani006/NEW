package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;
import java.util.Map;

public class RequestWrapper implements Serializable {

	private Map<String, Object> additionalParams;
	
	private Request request;

	public Map<String, Object> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(Map<String, Object> additionalParams) {
		this.additionalParams = additionalParams;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
}
