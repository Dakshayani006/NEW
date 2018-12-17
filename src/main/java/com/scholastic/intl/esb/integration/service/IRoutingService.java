package com.scholastic.intl.esb.integration.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IRoutingService {

	<T> T runRouteWithBody(String endpoint, Object body, Class<T> type) throws ExecutionException, InterruptedException;

	<T> T runRouteWithBody(String endpoint, Object body, Object header, Class<T> type)
			throws ExecutionException, InterruptedException;

	<T> T runRouteWithBodyForSnipp(String endpoint, Object body, Map<String, Object> header, Class<T> type)
			throws ExecutionException, InterruptedException;
	
}
