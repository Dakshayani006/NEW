package com.scholastic.intl.esb.integration.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	private final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	@Value("${Mail.serverUrl}")
	private String serverUrl;
	@Value("${Mail.recipient}")
	private String recipient;
	@Value("${Mail.from}")
	private String from;
	@Value("${Environment}")
    private String environment;

	@Produce
	private ProducerTemplate template;

	public void sendMail(String projectName,String forProject ,String serviceName, String inputRequest, Exception exception)
			throws Exception {
		logger.info("**********Inside sendMail*********");
		logger.info("**********Inside sendMail*********" + from + ":" + recipient + ":" + serverUrl);
		template.setDefaultEndpointUri(serverUrl);
		Map<String, Object> emailMap = new HashMap<String, Object>();
		emailMap.put("To", recipient);
		emailMap.put("From", from);
		//emailMap.put("Subject", "FUSE Exception on "+forProject+":"+serviceName);
		emailMap.put("Subject", "FUSE Exception on Env:"+environment+" "+forProject+":"+serviceName);
		String mailBody = "Hello Team,\n\n" + exception.getClass().getSimpleName() + " Occured for "+forProject+" request at service: "
				+ serviceName + " in interface: " + projectName +" \n\nExceptionMessage: " + exception.getMessage()
				+ "\n\nInput Request:\n" + inputRequest
				+ "\n\nRegards, \nIntegration Team\n\nThis is a system generated mail, please do not reply to this mail.";
		template.sendBodyAndHeaders(template.getDefaultEndpoint(), mailBody, emailMap);
		logger.info("**********Mail Send Successfully*********");
	}
}
