package com.scholastic.intl.esb.integration.wsdl.endpoint;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.scholastic.intl.esb.integration.exception.ApplicationException;
import com.scholastic.intl.esb.integration.exception.BusinessException;
import com.scholastic.intl.esb.integration.mediator.AuthorizationHeaderClass;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaSucResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.InsertChildCustToNSRequest;
import com.scholastic.intl.esb.integration.resource.InsertChildCustToNSResponse;
import com.scholastic.intl.esb.integration.resource.InsertChildRequestWrapper;
import com.scholastic.intl.esb.integration.resource.RequestWrapper;
import com.scholastic.intl.esb.integration.resource.TargetSystemOutputDetails;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.UpdateChildCustToNSRequest;
import com.scholastic.intl.esb.integration.resource.UpdateChildCustToNSResponse;
import com.scholastic.intl.esb.integration.resource.UpdateChildRequestWrapper;
import com.scholastic.intl.esb.integration.service.IRoutingService;
import com.scholastic.intl.esb.integration.service.CustomerMappingService;
import com.scholastic.intl.esb.integration.util.CommonUtil;
import com.scholastic.intl.esb.integration.util.EmailUtil;
import com.scholastic.intl.esb.integration.util.ExceptionUtil;

@Endpoint
public class CustomerEndpoint {
	private final Logger logger = LoggerFactory.getLogger(CustomerEndpoint.class);
    
	private IRoutingService routingService;

	@Value("${InterfaceName}")
	private String interfaceName;
	
	/*@Value("${SnippEndpointDynamic}")
	private String snippEndpointDynamic;*/
	
	@Value("${SnippAuthTokenPrefixDynamic}")
	private String snippAuthTokenPrefixDynamic;

	@Value("${AuSubsidiary}")
	private String auSubsidiary;
	
	@Autowired
	ExceptionUtil exceptionUtil;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	CustomerMappingService customerMappingService;
	
    public CustomerEndpoint(IRoutingService routingService) {
        this.routingService = routingService;
    }
    
	private static final String NAMESPACE_URI = "http://resource.integration.esb.intl.scholastic.com";
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addParentCustToJADERequest")
	@ResponsePayload
	public AddParentCustToJADEResponse addParentCustToJade(@RequestPayload AddParentCustToJADERequest inputRequest)
			throws ExecutionException, InterruptedException {
		AddParentCustToJADEResponse response = new AddParentCustToJADEResponse();
		try {
			if ("SNIPP".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callRva(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			} else if ("JADE".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callJade(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			} else if ("BOTH".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callRva(inputRequest, response));
				response.getTargetSystemOutputDetails().add(callJade(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			}
		} catch (Exception exception) {
			// response.setStatus("Failed");
			exceptionUtil.handleException(inputRequest, interfaceName, "Add Parent Customer Request", exception);
		}
		return response;
	}

	public TargetSystemOutputDetails callRva(AddParentCustToJADERequest inputRequest,
			AddParentCustToJADEResponse response) throws IOException {
		TargetSystemOutputDetails targetSystemOutputDetails;
		try {
			AddParentCustRvaRequest mappedRequest = customerMappingService.mapAddParentCustomerToRva(inputRequest);
			logger.info("Mapped request to Rva ----------" + CommonUtil.getObjectAsJsonString(mappedRequest));
			AddParentCustRvaSucResponse rvaResponse = routingService.runRouteWithBody("direct:ParentCustomerAdd",
					CommonUtil.getObjectAsJsonString(mappedRequest), "anz-customer-interface-parentCustRva",
					AddParentCustRvaSucResponse.class);
			logger.info("Response after calling Rva:-------" + CommonUtil.getObjectAsJsonString(rvaResponse));
			targetSystemOutputDetails = null;
			targetSystemOutputDetails = customerMappingService.mapRvaResponseToNetsuiteForAddParentCust(rvaResponse);
			logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
		} catch (Exception exception) {
			exception.printStackTrace();
			targetSystemOutputDetails = customerMappingService
					.mapRvaResponseForException(exception);
			//response.getTargetSystemOutputDetails().add(targetSystemOutputDetails);
		}
		return targetSystemOutputDetails;
	}

	public TargetSystemOutputDetails callJade(AddParentCustToJADERequest inputRequest,
			AddParentCustToJADEResponse response)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		TargetSystemOutputDetails targetSystemOutputDetails;
		String jadeResponse = "";
		try {
			String mappedRequest = customerMappingService.mapAddParentCustomerToJade(inputRequest);
			logger.info("Mapped request to Jade ----------" + mappedRequest);
			/*String jadeResponse = routingService.runRouteWithBody("direct:ParentCustomerAdd", mappedRequest,
					"anz-customer-interface-parentCustJade", String.class);*/
			if (auSubsidiary.equals(inputRequest.getParentCustData().getSubsidiary())) {
				jadeResponse = routingService.runRouteWithBody("direct:ParentCustomerAdd", mappedRequest,
						"anz-customer-interface-parentCustJadeAus", String.class);
			} else {
				jadeResponse = routingService.runRouteWithBody("direct:ParentCustomerAdd", mappedRequest,
						"anz-customer-interface-parentCustJade", String.class);
			}
			logger.info("Response after calling Jade " + "\n" + jadeResponse);
			targetSystemOutputDetails = customerMappingService.mapJadeResponseToNetsuiteForAddParentCust(jadeResponse);
			//logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
		} catch (Exception exception) {
			targetSystemOutputDetails = customerMappingService
					.mapJadeResponseForException(exception);
			//response.getTargetSystemOutputDetails().add(targetSystemOutputDetails);
		}
		return targetSystemOutputDetails;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updParentCustToJADERequest")
	@ResponsePayload
	public UpdParentCustToJADEResponse updateParentCustToJade(@RequestPayload UpdParentCustToJADERequest inputRequest)
			throws ExecutionException, InterruptedException {
		UpdParentCustToJADEResponse response = new UpdParentCustToJADEResponse();
		try {
			if ("SNIPP".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callRvaForUpd(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			} else if ("JADE".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callJadeForUpd(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			} else if ("BOTH".equals(inputRequest.getParentCustData().getDestinationSystem())) {
				response.getTargetSystemOutputDetails().add(callRvaForUpd(inputRequest, response));
				response.getTargetSystemOutputDetails().add(callJadeForUpd(inputRequest, response));
				logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
			}
		} catch (Exception exception) {
			// response.setStatus("Failed");
			exceptionUtil.handleException(inputRequest, interfaceName, "Add Parent Customer Request", exception);
		}
		return response;
	}

	public TargetSystemOutputDetails callRvaForUpd(UpdParentCustToJADERequest inputRequest,
			UpdParentCustToJADEResponse response) throws IOException {
		TargetSystemOutputDetails targetSystemOutputDetails;
		try {
			AddParentCustRvaRequest mappedRequest = customerMappingService
					.mapUpdParentCustomerToRva(inputRequest);
			logger.info("Mapped request to Rva ----------" + CommonUtil.getObjectAsJsonString(mappedRequest));
			AddParentCustRvaSucResponse rvaResponse = routingService.runRouteWithBody("direct:callRvaForUpdate",
					CommonUtil.getObjectAsJsonString(mappedRequest), "anz-customer-interface-parentCustRvaUpdate",
					AddParentCustRvaSucResponse.class);
			/*String dynamicUrl = snippEndpointDynamic + "/" + inputRequest.getParentCustData().getSnipmemberid();
			String urlWithParameters = "https://uat.snipp.ie/scholasticapi/member" + "/"
					+ inputRequest.getParentCustData().getSnipmemberid();
			String urlWithParameters = snippAuthTokenPrefixDynamic + "/"
					+ inputRequest.getParentCustData().getSnipmemberid();
			AuthorizationHeaderClass authorizationHeaderClass = new AuthorizationHeaderClass();
			String authorization = authorizationHeaderClass.mediate(urlWithParameters);
			logger.info("Authorization Token:---------" + authorization);
			Map<String, Object> header = new HashMap<String,Object>();
			header.put("Authorization", authorization);
			//header.put("MemberId", inputRequest.getParentCustData().getSnipmemberid());
			header.put("DynamicUrlSnipp", dynamicUrl);
			AddParentCustSnippResponse snippResponse = routingService.runRouteWithBodyForSnipp("direct:callSnippForUpdate",
					CommonUtil.getObjectAsJsonString(mappedRequest), header,
					AddParentCustSnippResponse.class);*/
			logger.info("Response after calling Rva:-------" + CommonUtil.getObjectAsJsonString(rvaResponse));
			targetSystemOutputDetails = customerMappingService.mapRvaResponseToNetsuiteForUpdParentCust(rvaResponse);
			logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
		} catch (Exception exception) {
			targetSystemOutputDetails = customerMappingService
					.mapRvaResponseForException(exception);
			//response.getTargetSystemOutputDetails().add(targetSystemOutputDetails);
		}
		return targetSystemOutputDetails;
	}
	
	public TargetSystemOutputDetails callJadeForUpd(UpdParentCustToJADERequest inputRequest,
			UpdParentCustToJADEResponse response)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		TargetSystemOutputDetails targetSystemOutputDetails;
		String jadeResponse = "";
		try {
			String mappedRequest = customerMappingService.mapUpdateParentCustomerToJade(inputRequest);
			logger.info("Mapped request to Jade ----------" + mappedRequest);
			/*String jadeResponse = routingService.runRouteWithBody("direct:callJadeForUpdate", mappedRequest,
					"anz-customer-interface-parentCustJade", String.class);*/
			if (auSubsidiary.equals(inputRequest.getParentCustData().getSubsidiary())) {
				jadeResponse = routingService.runRouteWithBody("direct:callJadeForUpdateAus", mappedRequest,
						"anz-customer-interface-parentCustJade", String.class);
			} else {
				jadeResponse = routingService.runRouteWithBody("direct:callJadeForUpdate", mappedRequest,
						"anz-customer-interface-parentCustJade", String.class);
			}
			logger.info("Response after calling Jade " + "\n" + jadeResponse);
			targetSystemOutputDetails = customerMappingService.mapJadeResponseToNetsuiteForUpdParentCust(jadeResponse);
			//logger.info("Response returned to netsuite " + "\n" + CommonUtil.getPrettyPrintStringForXml(response));
		} catch (Exception exception) {
			targetSystemOutputDetails = customerMappingService
					.mapJadeResponseForException(exception);
			//response.getTargetSystemOutputDetails().add(targetSystemOutputDetails);
		}
		return targetSystemOutputDetails;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "insertChildCustToNSRequest")
	@ResponsePayload
	public InsertChildCustToNSResponse insertChildCust(@RequestPayload InsertChildCustToNSRequest request)
			throws ExecutionException, InterruptedException, IOException {
		InsertChildCustToNSResponse response = new InsertChildCustToNSResponse();
		String res = "";
		try {
			InsertChildRequestWrapper insertChildRequestWrapper = new InsertChildRequestWrapper();
			insertChildRequestWrapper.setRequest(request);
			RequestWrapper requestWrapper = new RequestWrapper();
			requestWrapper.setRequest(insertChildRequestWrapper);
			requestWrapper.setAdditionalParams(customerMappingService.getAdditionalParamsForInsertChildCust(request));
			/*Map<String, Object> additionalParams = new HashMap<>();
			additionalParams.put("JadeId", request.getExternalId());*/
			//requestWrapper.setAdditionalParams(additionalParams);
			logger.info("Input sent for Queuing for InsertChildCustomer ----------" + CommonUtil.getPrettyPrintString(requestWrapper));
			res = routingService.runRouteWithBody("direct:addToQueueForInsertChildCust", requestWrapper, String.class);
			logger.info("After calling to queue: " + res);
			response.setStatus(res);
		} catch (Exception exception) {
			response.setStatus("Failed");
			exceptionUtil.handleException(request, interfaceName, "Palm InsertChildCustomer to Netsuite", exception);
		}
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateChildCustToNSRequest")
	@ResponsePayload
	public UpdateChildCustToNSResponse updateChildCust(@RequestPayload UpdateChildCustToNSRequest request)
			throws ExecutionException, InterruptedException, IOException {
		UpdateChildCustToNSResponse response = new UpdateChildCustToNSResponse();
		String res = "";
		try {
			UpdateChildRequestWrapper updateChildRequestWrapper = new UpdateChildRequestWrapper();
			updateChildRequestWrapper.setRequest(request);
			RequestWrapper requestWrapper = new RequestWrapper();
			requestWrapper.setRequest(updateChildRequestWrapper);
			requestWrapper.setAdditionalParams(customerMappingService.getAdditionalParamsForUpdateChildCust(request));
			/*
			 * Map<String, Object> additionalParams = new HashMap<>();
			 * additionalParams.put("CustomerInternalId",
			 * request.getUpdateChildCustData().getCustomerinternalid());
			 */
			// requestWrapper.setAdditionalParams(additionalParams);
			logger.info("Input sent for Queuing for UpdateChildCustomer ----------" + CommonUtil.getPrettyPrintString(requestWrapper));
			res = routingService.runRouteWithBody("direct:addToQueueForUpdateChildCust", requestWrapper, String.class);
			logger.info("After calling to queue: " + res);
			response.setStatus(res);
		} catch (Exception exception) {
			response.setStatus("Failed");
			exceptionUtil.handleException(request, interfaceName, "Palm UpdateChildCustomer to Netsuite", exception);
		}
		return response;
	}
	
}