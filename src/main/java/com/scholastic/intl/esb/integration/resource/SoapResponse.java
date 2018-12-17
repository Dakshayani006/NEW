package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

public class SoapResponse extends AdditionalParams implements Serializable {

	private static final long serialVersionUID = 4493110108725894168L;
	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
