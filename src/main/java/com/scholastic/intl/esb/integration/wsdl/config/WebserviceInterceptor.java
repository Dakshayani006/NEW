package com.scholastic.intl.esb.integration.wsdl.config;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.scholastic.intl.esb.integration.util.CommonUtil;

@Component
public class WebserviceInterceptor implements EndpointInterceptor{

	private final Logger logger = LoggerFactory.getLogger(WebserviceInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		SaajSoapMessage saajSoapMessage=(SaajSoapMessage) messageContext.getRequest();
		logger.info("Input --------------------\n"+StringEscapeUtils.unescapeHtml4(CommonUtil.getPrettyPrintStringForXml(saajSoapMessage.getSaajMessage().getSOAPPart().getEnvelope())));
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
