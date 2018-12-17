package com.scholastic.intl.esb.integration.util;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scholastic.intl.esb.integration.exception.ApplicationException;
import com.scholastic.intl.esb.integration.exception.BusinessException;

@Component
public class ExceptionUtil {
	private final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	@Autowired
	EmailUtil emailUtil;

	public void handleException(Object inputRequest, String interfaceName, String failedService, Exception exception) {
		logger.error("Exception occured ", exception);
		try {
			if (exception instanceof CamelExecutionException
					&& exception.getCause() instanceof HttpOperationFailedException) {
				exception = new BusinessException(
						exception.getCause().getMessage() + " \n\nresponse body:\n " + 
								((HttpOperationFailedException) exception.getCause()).getResponseBody());
			} else if (!(exception instanceof BusinessException)) {
				exception = new ApplicationException(exception.getClass().getName()+" : "+exception.getMessage());
			}
			if(inputRequest instanceof String){
				emailUtil.sendMail(interfaceName, interfaceName, failedService,
						CommonUtil.getPrettyPrintStringForXml(String.valueOf(inputRequest)), exception);
			}else{
				emailUtil.sendMail(interfaceName, interfaceName, failedService,
						CommonUtil.getPrettyPrintStringForXml(inputRequest), exception);
			}
			
		} catch (Exception mailException) {
			logger.error("Exception occured while sending mail ", mailException);
		}
	}
	public String getFailedResponseForWebReq(){
		return "{\"status\":\"Failed\"}";
	}
	public String getFailedResponseForUpdateReq(){
		return "{\"authFailureMessage\":null,\"status\":\"FAILED\"}";
	}
	public String getHttpFailedResponseForUpdateReq(){
		return "{\"authFailureMessage\":null,\"status\":\"HTTP_FAILED\"}";
	}
}
