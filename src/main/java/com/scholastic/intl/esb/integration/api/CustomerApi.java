package com.scholastic.intl.esb.integration.api;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.scholastic.intl.esb.integration.resource.RequestWrapper;
//import com.scholastic.intl.esb.integration.resource.ErrorResponse;
import com.scholastic.intl.esb.integration.resource.SoapResponse;
import com.scholastic.intl.esb.integration.service.CustomerMappingService;
import com.scholastic.intl.esb.integration.service.IRoutingService;
import com.scholastic.intl.esb.integration.util.CommonUtil;
import com.scholastic.intl.esb.integration.util.EmailUtil;
import com.scholastic.intl.esb.integration.util.ExceptionUtil;

import io.swagger.annotations.Api;

@Path("/customer")
@Api("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class CustomerApi {

	private final Logger logger = LoggerFactory.getLogger(CustomerApi.class);

	private IRoutingService routingService;

	@Value("${InterfaceName}")
	private String interfaceName;

	@Value("${valid.token}")
	private String validToken;

	@Value("${snapshot.version}")
	private String snapshotVersion;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	ExceptionUtil exceptionUtil;

	@Autowired
	CustomerMappingService customerMappingService;
	
	@Value("${Environment}")
    private String environment;
	
	@Value("${InsertCustomerUpdateEndpoint}")
	private String insertCustomerUpdateEndpoint;
	@Value("${JadeEndpointAus}")
	private String jadeEndpointAus;
	@Value("${AuSubsidiary}")
	private String auSubsidiary;
	private static final String SOAP_ACTION="updateClient";
	
	public CustomerApi(IRoutingService routingService) {
		this.routingService = routingService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/private/insertChildCust")
	public String updatePalmForInsertChildCust(final SoapResponse inputRequest) throws ExecutionException, InterruptedException {
		String status = "", internalId = "", message = "", jadeId = "", code = "", apiResponse = null, endpoint = null,
				contentType = null, soapAction = null, request = null, palmUpdateResponse = "";
		try {
			logger.info("input request for updating insertChild for interface " + interfaceName + "\n"
					+ CommonUtil.getPrettyPrintString(inputRequest));
			DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new ByteArrayInputStream(inputRequest.getResponse().getBytes()));
			document.getDocumentElement().normalize();
			XPathExpression successXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='writeResponse']/status/@isSuccess");
			status = successXp.evaluate(document);
			XPathExpression internalIdXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='baseRef']/@internalId");
			internalId = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), internalIdXp);
			XPathExpression messageXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='message']/text()");
			message = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), messageXp);
			XPathExpression codeXp = XPathFactory.newInstance().newXPath().compile("//*[local-name()='code']/text()");
			code = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), codeXp);
			jadeId = (String) inputRequest.getAdditionalParams().get("JadeId");
			String reason = code + ":" + message;
			request = customerMappingService.updatePalmForInsertCustChild(status, reason, jadeId, internalId);
			logger.info("Before sending to Palm ----------" + request);
			String subsidiary=String.valueOf(inputRequest.getAdditionalParams().get("Subsidiary"));
			contentType=CommonUtil.XML;
			soapAction=SOAP_ACTION;
			endpoint=insertCustomerUpdateEndpoint;
			/*String palmUpdateResponse=routingService.runRouteWithBody("direct:updateBackToPalm", request,
					"anz-customer-interface-insertChild", String.class);*/
			if(auSubsidiary.equals(subsidiary)){
				endpoint=jadeEndpointAus;
				palmUpdateResponse = routingService.runRouteWithBody("direct:updateBackToPalm", request,
						"anz-customer-interface-insertChildAus", String.class);
			}else{
				endpoint=insertCustomerUpdateEndpoint;
				palmUpdateResponse = routingService.runRouteWithBody("direct:updateBackToPalm", request,
						"anz-customer-interface-insertChild", String.class);
			}
			apiResponse = CommonUtil.getPrettyPrintStringForXml(palmUpdateResponse);
			logger.info("updatePalm response for InsertChildCust ----------" + apiResponse);
		} catch (Exception exception) {
			if (exception instanceof CamelExecutionException
					&& exception.getCause() instanceof HttpOperationFailedException) {
				apiResponse = exceptionUtil.getHttpFailedResponseForUpdateReq();
				mapAndSendForReprocessing(endpoint, contentType, soapAction, request, "anz-customer-interface-insertChild");
			} else {
			apiResponse = exceptionUtil.getFailedResponseForUpdateReq();
			exceptionUtil.handleException(inputRequest.getAdditionalParams().get("WebRequest"), interfaceName,
					"Netsuite to Palm Update", exception);
			}
		}

		return apiResponse;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/private/updateChildCust")
	public String updatePalmForUpdateChildCust(final SoapResponse inputRequest) throws ExecutionException, InterruptedException {
		String status = "", internalId = "", message = "", code = "", apiResponse = null, endpoint = null,
				contentType = null, soapAction = null, request = null, palmUpdateResponse = "";
		try {
			logger.info("input request for updating updateChild for interface " + interfaceName + "\n"
					+ CommonUtil.getPrettyPrintString(inputRequest));
			DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new ByteArrayInputStream(inputRequest.getResponse().getBytes()));
			document.getDocumentElement().normalize();
			XPathExpression successXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='writeResponse']/status/@isSuccess");
			status = successXp.evaluate(document);
			XPathExpression internalIdXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='baseRef']/@internalId");
			internalId = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), internalIdXp);
			XPathExpression messageXp = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='message']/text()");
			message = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), messageXp);
			XPathExpression codeXp = XPathFactory.newInstance().newXPath().compile("//*[local-name()='code']/text()");
			code = CommonUtil.extractXpathFromXml(inputRequest.getResponse(), codeXp);
			String customerInternalId = (String) inputRequest.getAdditionalParams().get("CustomerInternalId");
			String reason = code + ":" + message;
			request = customerMappingService.updatePalmForUpdateCustChild(status, reason, customerInternalId,
					internalId);
			logger.info("Before sending to Palm ----------" + request);
			String subsidiary=String.valueOf(inputRequest.getAdditionalParams().get("Subsidiary"));
			contentType=CommonUtil.XML;
			soapAction=SOAP_ACTION;
			endpoint=insertCustomerUpdateEndpoint;
			/*String palmUpdateResponse=routingService.runRouteWithBody("direct:updateBackToPalm", request,
					"anz-customer-interface-updateChild", String.class);*/
			if(auSubsidiary.equals(subsidiary)){
				endpoint=jadeEndpointAus;
				palmUpdateResponse = routingService.runRouteWithBody("direct:updateBackToPalm", request,
						"anz-customer-interface-updateChildAus", String.class);
			}else{
				endpoint=insertCustomerUpdateEndpoint;
				palmUpdateResponse = routingService.runRouteWithBody("direct:updateBackToPalm", request,
						"anz-customer-interface-updateChild", String.class);
			}
			apiResponse = CommonUtil.getPrettyPrintStringForXml(palmUpdateResponse);
			logger.info("updatePalm response for updateChildCust ----------" + apiResponse);
		} catch (Exception exception) {
			if (exception instanceof CamelExecutionException
					&& exception.getCause() instanceof HttpOperationFailedException) {
				apiResponse = exceptionUtil.getHttpFailedResponseForUpdateReq();
				mapAndSendForReprocessing(endpoint, contentType, soapAction, request, "anz-customer-interface-updateChild");
			} else {
			apiResponse = exceptionUtil.getFailedResponseForUpdateReq();
			exceptionUtil.handleException(inputRequest.getAdditionalParams().get("WebRequest"), interfaceName,
					"Netsuite to Palm Update", exception);
			}
		}

		return apiResponse;

	}
	
	
	/**This method is used to map and send a failed request for reprocessing.
	 * @param endpoint
	 * @param contentType
	 * @param soapAction
	 * @param request
	 * @param interfaceName
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private void mapAndSendForReprocessing(String endpoint, String contentType, String soapAction,
			String request,String interfaceName) throws ExecutionException, InterruptedException {
		RequestWrapper requestWrapper=customerMappingService.mapRequestForReprocessing(endpoint, contentType, soapAction, request,interfaceName);
		routingService.runRouteWithBody("direct:addToQueueFailureQueue", requestWrapper, String.class);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/healthCheck")
	public String healthCheck() {
		return "{\"version\":\"" + snapshotVersion + "\"}";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/env")
	public String envCheck() {
		return "{\"environment\":\"" + environment + "\"}";
	}

}
