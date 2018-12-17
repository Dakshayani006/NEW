package com.scholastic.intl.esb.integration.service;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scholastic.intl.esb.integration.exception.BusinessException;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaSucResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.InsertChildCustToNSRequest;
import com.scholastic.intl.esb.integration.resource.RequestWrapper;
import com.scholastic.intl.esb.integration.resource.SoapRequest;
import com.scholastic.intl.esb.integration.resource.SoapResponse;
import com.scholastic.intl.esb.integration.resource.TargetSystemOutputDetails;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.UpdateChildCustToNSRequest;

public interface IMappingService {

	String mapPropertiesFromFile(String propertyName);

	//AddParentCustSnippRequest mapAddParentCustomerToSnipp(AddParentCustToJADERequest addParentCustToJADERequest) throws JAXBException;

	//TargetSystemOutputDetails mapSnippResponseToNetsuiteForAddParentCust(AddParentCustSnippResponse snippResponse);

	String mapAddParentCustomerToJade(AddParentCustToJADERequest addParentCustToJADERequest) throws JAXBException;

	TargetSystemOutputDetails mapJadeResponseToNetsuiteForAddParentCust(String jadeResponse) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException;

	TargetSystemOutputDetails mapJadeResponseForException(Exception exception)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException;

	//TargetSystemOutputDetails mapSnippResponseForException(Exception exception) throws IOException;

	/*AddParentCustSnippRequest mapUpdParentCustomerToSnipp(UpdParentCustToJADERequest updParentCustToJADERequest)
			throws JAXBException;*/

	String mapUpdateParentCustomerToJade(UpdParentCustToJADERequest updParentCustToJADERequest) throws JAXBException;

	//TargetSystemOutputDetails mapSnippResponseToNetsuiteForUpdParentCust(AddParentCustSnippResponse snippResponse);

	TargetSystemOutputDetails mapJadeResponseToNetsuiteForUpdParentCust(String jadeResponse)
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException;

	Map<String, Object> getAdditionalParamsForInsertChildCust(InsertChildCustToNSRequest insertChildCustToNSRequest) throws JsonProcessingException, IOException, TransformerException, ParserConfigurationException, SAXException;

	Map<String, Object> getAdditionalParamsForUpdateChildCust(UpdateChildCustToNSRequest updateChildCustToNSRequest)
			throws IOException, TransformerException, ParserConfigurationException, SAXException;

	String updatePalmForInsertCustChild(String status, String message, String jadeId, String internalId)
			throws IOException, ParserConfigurationException, SAXException, DatatypeConfigurationException,
			JAXBException;

	String updatePalmForUpdateCustChild(String status, String message, String customerInternalId, String internalId)
			throws IOException, ParserConfigurationException, SAXException, DatatypeConfigurationException,
			JAXBException;

	RequestWrapper mapRequestForReprocessing(String endpoint, String contentType, String soapAction, String request,
			String interfaceName);

	AddParentCustRvaRequest mapAddParentCustomerToRva(AddParentCustToJADERequest addParentCustToJADERequest);

	TargetSystemOutputDetails mapRvaResponseToNetsuiteForAddParentCust(AddParentCustRvaSucResponse rvaResponse);

	AddParentCustRvaRequest mapUpdParentCustomerToRva(UpdParentCustToJADERequest updParentCustToJADERequest)
			throws JAXBException;

	TargetSystemOutputDetails mapRvaResponseToNetsuiteForUpdParentCust(AddParentCustRvaSucResponse rvaResponse);

	TargetSystemOutputDetails mapRvaResponseForException(Exception exception) throws IOException;


}
