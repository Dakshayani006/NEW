package com.scholastic.intl.esb.integration.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoutingService implements IRoutingService{

	private final Logger logger = LoggerFactory.getLogger(RoutingService.class);
	
	@Produce
    private ProducerTemplate template;

	public <T> T runRouteWithBody(String endpoint, Object body, Class<T> type) throws ExecutionException, InterruptedException {
        logger.info("***Inside RoutingService runRouteWithBody method***");
    	template.setDefaultEndpointUri(endpoint);
    	if(endpoint.contains("addToQueue"))
    	{
    		return type.cast(template.asyncSendBody(template.getDefaultEndpoint(), body).get());
    	}
    	else
    		template.requestBody(template.getDefaultEndpoint(), body);
        return type.cast("{\"status\":\"added to queue successfully\"}");
    }
	
	@Override
	public <T> T runRouteWithBody(String endpoint, Object body, Object header, Class<T> type)
			throws ExecutionException, InterruptedException {
		logger.info("***Inside RoutingService runRouteWithBody method***");
		template.setDefaultEndpointUri(endpoint);
		return type.cast(template.requestBodyAndHeader(template.getDefaultEndpoint(), body, "Interface_Name", header));
	}
	
	@Override
	public <T> T runRouteWithBodyForSnipp(String endpoint, Object body, Map<String, Object> header, Class<T> type)
			throws ExecutionException, InterruptedException {
		logger.info("***Inside RoutingService runRouteWithBody method***");
		template.setDefaultEndpointUri(endpoint);
		//return type.cast(template.requestBodyAndHeader(template.getDefaultEndpoint(), body, "Authorization", header));
		return type.cast(template.requestBodyAndHeaders(endpoint, body, header));
	}

    
}
