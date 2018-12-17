package com.scholastic.intl.esb.integration.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
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
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaErrorResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustRvaSucResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippRequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.AddParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.Address;
import com.scholastic.intl.esb.integration.resource.AddressRva;
import com.scholastic.intl.esb.integration.resource.ContactDetail;
import com.scholastic.intl.esb.integration.resource.CountryRva;
import com.scholastic.intl.esb.integration.resource.FailedRequest;
import com.scholastic.intl.esb.integration.resource.InsertChildCustToNSRequest;
import com.scholastic.intl.esb.integration.resource.MemberSubType;
import com.scholastic.intl.esb.integration.resource.NzConfigMapper;
import com.scholastic.intl.esb.integration.resource.PersonalDetails;
import com.scholastic.intl.esb.integration.resource.RequestWrapper;
import com.scholastic.intl.esb.integration.resource.TargetSystemOutputDetails;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADERequest;
import com.scholastic.intl.esb.integration.resource.UpdParentCustToJADEResponse;
import com.scholastic.intl.esb.integration.resource.UpdateChildCustToNSRequest;
import com.scholastic.intl.esb.integration.resource.UserTypeRva;
import com.scholastic.intl.esb.integration.util.CommonUtil;
import com.scholastic.intl.esb.integration.wsdl.palm.AddParent;
import com.scholastic.intl.esb.integration.wsdl.palm.NSIClient;
import com.scholastic.intl.esb.integration.wsdl.palm.NSIOrganisation;
import com.scholastic.intl.esb.integration.wsdl.palm.NetsuiteAddParentCustEnvelope;
import com.scholastic.intl.esb.integration.wsdl.palm.NetsuiteAddParentCustSoapBody;
import com.scholastic.intl.esb.integration.wsdl.palm.NetsuiteUpdParentCustEnvelope;
import com.scholastic.intl.esb.integration.wsdl.palm.NetsuiteUpdParentCustSoapBody;
import com.scholastic.intl.esb.integration.wsdl.palm.PalmBody;
import com.scholastic.intl.esb.integration.wsdl.palm.PalmEnvelope;
import com.scholastic.intl.esb.integration.wsdl.palm.UpdateClient;
import com.scholastic.intl.esb.integration.wsdl.palm.UpdateParent;

@Service
public class CustomerMappingService implements IMappingService {

	private final Logger logger = LoggerFactory.getLogger(CustomerMappingService.class);

	@Autowired
	NzConfigMapper nzConfigMapper;

	@Value("${valid.token}")
	private String validToken;

	@Value("${Netsuite.Item.email}")
	private String netsuiteItemEmail;

	@Value("${Netsuite.Item.password}")
	private String netsuiteItemPassword;

	@Value("${Netsuite.Item.account}")
	private String netsuiteItemAccount;

	//snipp related code
	/*@Override
	public AddParentCustSnippRequest mapAddParentCustomerToSnipp(AddParentCustToJADERequest addParentCustToJADERequest) throws JAXBException {
		logger.info("***Inside CustomerMappingService mapAddParentCustomerToSnipp***");
		AddParentCustSnippRequest addParentCustSnippRequest = new AddParentCustSnippRequest();
		addParentCustSnippRequest.setExtReference(CommonUtil.convertEmptyToNull(String.valueOf(addParentCustToJADERequest.getParentCustData().getNSInternalID())));
		MemberSubType memberSubType = new MemberSubType();
		memberSubType.setName("DD");
		addParentCustSnippRequest.setMemberSubType(memberSubType);
		//addParentCustSnippRequest.setMemberStatus(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getMemberStatus()));
		addParentCustSnippRequest.setMemberStatus("Active");
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setFirstName("-");
		personalDetails.setLastName(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getName()));
		addParentCustSnippRequest.setPersonalDetails(personalDetails);
		List<Address> addressList = new ArrayList<Address>();
		Address address = new Address();
		address.setAddressLine1(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getAddr1()));
		address.setAddressLine2(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getAddr2()));
		address.setCity(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getCity()));
		address.setZip(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getZip()));
		//address.setCountryId(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getSnippCountryCode()));
		address.setCountryId("NZ");
		addressList.add(address);
		addParentCustSnippRequest.setAddress(addressList);
		List<Account> accountList = new ArrayList<Account>();
		Account account = new Account();
		account.setCurrencyId(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getCurrencyId()));
		accountList.add(account);
		addParentCustSnippRequest.setAccount(accountList);
		List<ContactDetail> contactDetailsList = new ArrayList<ContactDetail>();
		ContactDetail contactDetail = new ContactDetail();
		contactDetail.setEmail(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getEmail()));
		contactDetail.setPhone(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getPhone()));
		contactDetail.setMobilePhone(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getMobile()));
		contactDetail.setFax(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getFax()));
		contactDetailsList.add(contactDetail);
		addParentCustSnippRequest.setContactDetails(contactDetailsList);
		List<ExtensionDatum> extensionDataList = new ArrayList<ExtensionDatum>();
		ExtensionDatum extensionDatum1 = new ExtensionDatum();
		extensionDatum1.setPropertyName("Book Fair ABCD Marker");
		extensionDatum1.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getBookFairABCDMarker()));
		extensionDataList.add(extensionDatum1);
		ExtensionDatum extensionDatum2 = new ExtensionDatum();
		extensionDatum2.setPropertyName("Decile");
		extensionDatum2.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getDecile()));
		extensionDataList.add(extensionDatum2);
		ExtensionDatum extensionDatum3 = new ExtensionDatum();
		extensionDatum3.setPropertyName("Fair Size");
		extensionDatum3.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getFairSize()));
		extensionDataList.add(extensionDatum3);
		ExtensionDatum extensionDatum4 = new ExtensionDatum();
		extensionDatum4.setPropertyName("Pricing Group");
		extensionDatum4.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getPricingGroup()));
		extensionDataList.add(extensionDatum4);
		ExtensionDatum extensionDatum5 = new ExtensionDatum();
		extensionDatum5.setPropertyName("Region");
		extensionDatum5.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getRegion()));
		extensionDataList.add(extensionDatum5);
		ExtensionDatum extensionDatum6 = new ExtensionDatum();
		extensionDatum6.setPropertyName("Subsidiary");
		extensionDatum6.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getSubsidiary()));
		extensionDataList.add(extensionDatum6);
		ExtensionDatum extensionDatum7 = new ExtensionDatum();
		extensionDatum7.setPropertyName("Zone Number");
		extensionDatum7.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getZoneNumber()));
		extensionDataList.add(extensionDatum7);
		addParentCustSnippRequest.setExtensionData(extensionDataList);
		return addParentCustSnippRequest;
	}*/
	
	@Override
	public AddParentCustRvaRequest mapAddParentCustomerToRva(AddParentCustToJADERequest addParentCustToJADERequest) {
		logger.info("***Inside CustomerMappingService mapAddParentCustomerToRva***");
		AddParentCustRvaRequest addParentCustRvaRequest = new AddParentCustRvaRequest();
		addParentCustRvaRequest.setAddress(new AddressRva());
		//address block starts
		addParentCustRvaRequest.getAddress().setAddress1(addParentCustToJADERequest.getParentCustData().getAddr1());
		addParentCustRvaRequest.getAddress().setAddress2(addParentCustToJADERequest.getParentCustData().getAddr2());
		addParentCustRvaRequest.getAddress().setAddress3(addParentCustToJADERequest.getParentCustData().getAddr3());
		addParentCustRvaRequest.getAddress().setCity(addParentCustToJADERequest.getParentCustData().getCity());
		addParentCustRvaRequest.getAddress().setCountry(new CountryRva());
		//country block starts
		addParentCustRvaRequest.getAddress().getCountry().setCreatedDate(addParentCustToJADERequest.getParentCustData().getCountrycreatedate());
		addParentCustRvaRequest.getAddress().getCountry().setCurrency(addParentCustToJADERequest.getParentCustData().getCtrycurrency());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getCtryid())) {
			addParentCustRvaRequest.getAddress().getCountry()
					.setId(Integer.valueOf(addParentCustToJADERequest.getParentCustData().getCtryid()));
		}
		addParentCustRvaRequest.getAddress().getCountry().setIsoCode(addParentCustToJADERequest.getParentCustData().getCtryisoCode());
		addParentCustRvaRequest.getAddress().getCountry().setName(addParentCustToJADERequest.getParentCustData().getCountry());
		addParentCustRvaRequest.getAddress().getCountry().setStatus(addParentCustToJADERequest.getParentCustData().getCtrystatus());
		addParentCustRvaRequest.getAddress().getCountry().setTimeZone(addParentCustToJADERequest.getParentCustData().getCtrytimeZone());
		addParentCustRvaRequest.getAddress().getCountry().setUpdatedDate(addParentCustToJADERequest.getParentCustData().getCtryupdatedDate());
		addParentCustRvaRequest.getAddress().getCountry().setUtc(addParentCustToJADERequest.getParentCustData().getUtc());
		//country block ends
		addParentCustRvaRequest.getAddress().setCreatedDate(addParentCustToJADERequest.getParentCustData().getCreateddate());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getAddid())) {
			addParentCustRvaRequest.getAddress()
					.setId(Integer.valueOf(addParentCustToJADERequest.getParentCustData().getAddid()));
		}
		addParentCustRvaRequest.getAddress().setName(addParentCustToJADERequest.getParentCustData().getAddressee());
		addParentCustRvaRequest.getAddress().setState(addParentCustToJADERequest.getParentCustData().getState());
		addParentCustRvaRequest.getAddress().setStatus(addParentCustToJADERequest.getParentCustData().getStatus());
		addParentCustRvaRequest.getAddress().setUpdatedDate(addParentCustToJADERequest.getParentCustData().getUpdatedDate());
		addParentCustRvaRequest.getAddress().setZipCode(addParentCustToJADERequest.getParentCustData().getZip());
		//address block ends
		addParentCustRvaRequest.setClientId(String.valueOf(addParentCustToJADERequest.getParentCustData().getNSInternalID()));
		addParentCustRvaRequest.setCreatedDate(addParentCustToJADERequest.getParentCustData().getCustcdate());
		addParentCustRvaRequest.setEmail(addParentCustToJADERequest.getParentCustData().getEmail());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getExtaddid())) {
			addParentCustRvaRequest.setExtAddressId(
					Integer.valueOf(addParentCustToJADERequest.getParentCustData().getExtaddid()));
		}
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getId())) {
			addParentCustRvaRequest.setId(Integer.valueOf(addParentCustToJADERequest.getParentCustData().getId()));
		}
		addParentCustRvaRequest.setName(addParentCustToJADERequest.getParentCustData().getName());
		addParentCustRvaRequest.setOrgId(addParentCustToJADERequest.getParentCustData().getOrgId());
		addParentCustRvaRequest.setOrgName(addParentCustToJADERequest.getParentCustData().getOrgName());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getRewardholder())) {
			addParentCustRvaRequest.setRewardHolder(
					Boolean.valueOf(addParentCustToJADERequest.getParentCustData().getRewardholder()));
		}
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getScholasticSubCode())) {
			addParentCustRvaRequest.setScholasticSubCode(
					Integer.valueOf(addParentCustToJADERequest.getParentCustData().getScholasticSubCode()));
		}
		addParentCustRvaRequest.setStatus(addParentCustToJADERequest.getParentCustData().getCuststatus());
		addParentCustRvaRequest.setUpdatedDate(addParentCustToJADERequest.getParentCustData().getCustcdate());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getUserstatus())) {
			addParentCustRvaRequest.setUserStatus(
					Boolean.valueOf(addParentCustToJADERequest.getParentCustData().getUserstatus()));
		}
		addParentCustRvaRequest.setUserType(new UserTypeRva());
		//user type block starts
		addParentCustRvaRequest.getUserType().setCreatedDate(addParentCustToJADERequest.getParentCustData().getUcreatedDate());
		if (!CommonUtil.isNullOrEmpty(addParentCustToJADERequest.getParentCustData().getUid())) {
			addParentCustRvaRequest.getUserType()
					.setId(Integer.valueOf(addParentCustToJADERequest.getParentCustData().getUid()));
		}
		addParentCustRvaRequest.getUserType().setName(addParentCustToJADERequest.getParentCustData().getUname());
		addParentCustRvaRequest.getUserType().setStatus(addParentCustToJADERequest.getParentCustData().getUstatus());
		addParentCustRvaRequest.getUserType().setUpdatedDate(addParentCustToJADERequest.getParentCustData().getUupdatedDate());
		//user type block ends
		return addParentCustRvaRequest;
	}

	//snipp related code
	/*@Override
	public TargetSystemOutputDetails mapSnippResponseToNetsuiteForAddParentCust(AddParentCustSnippResponse snippResponse) {
		logger.info("***Inside CustomerMappingService mapSnippResponseToNetsuiteForAddParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus(snippResponse.getSuccess());
		targetSystemOutputDetails.setMemberid(snippResponse.getData());
		targetSystemOutputDetails.setErrorresponse(snippResponse.getMessage());
		return targetSystemOutputDetails;
	}*/
	
	@Override
	public TargetSystemOutputDetails mapRvaResponseToNetsuiteForAddParentCust(AddParentCustRvaSucResponse rvaResponse) {
		logger.info("***Inside CustomerMappingService mapRvaResponseToNetsuiteForAddParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus("true");
		//targetSystemOutputDetails.setMemberid(snippResponse.getData());
		//targetSystemOutputDetails.setErrorresponse(snippResponse.getMessage());
		return targetSystemOutputDetails;
	}
	
	@Override
	public String mapAddParentCustomerToJade(AddParentCustToJADERequest addParentCustToJADERequest) throws JAXBException {
		logger.info("***Inside CustomerMappingService mapAddParentCustomerToJade method***");
		String request = "";
		AddParent addParent = new AddParent();
		NSIOrganisation nsiOrganisation = new NSIOrganisation();
		nsiOrganisation.setAddr1(addParentCustToJADERequest.getParentCustData().getAddr1());
		nsiOrganisation.setAddr2(addParentCustToJADERequest.getParentCustData().getAddr2());
		nsiOrganisation.setCity(addParentCustToJADERequest.getParentCustData().getCity());
		nsiOrganisation.setCountry(addParentCustToJADERequest.getParentCustData().getCountry());
		nsiOrganisation.setEmail(addParentCustToJADERequest.getParentCustData().getEmail());
		nsiOrganisation.setFax(addParentCustToJADERequest.getParentCustData().getFax());
		nsiOrganisation.setMobile(addParentCustToJADERequest.getParentCustData().getMobile());
		nsiOrganisation.setNSInternalID(addParentCustToJADERequest.getParentCustData().getNSInternalID());
		nsiOrganisation.setName(addParentCustToJADERequest.getParentCustData().getName());
		nsiOrganisation.setPhone(addParentCustToJADERequest.getParentCustData().getPhone());
		nsiOrganisation.setSalesRep(addParentCustToJADERequest.getParentCustData().getSalesRep());
		nsiOrganisation.setState(addParentCustToJADERequest.getParentCustData().getState());
		nsiOrganisation.setTerritory(addParentCustToJADERequest.getParentCustData().getTerritory());
		nsiOrganisation.setUrl(addParentCustToJADERequest.getParentCustData().getUrl());
		nsiOrganisation.setZip(addParentCustToJADERequest.getParentCustData().getZip());
		nsiOrganisation.setExternalId(addParentCustToJADERequest.getParentCustData().getLegacyId());
		addParent.setOrganisation(nsiOrganisation);
		NetsuiteAddParentCustEnvelope envelope = new NetsuiteAddParentCustEnvelope();
		envelope.setBody(new NetsuiteAddParentCustSoapBody());
		envelope.getBody().setAddParent(addParent);
		request = CommonUtil.convertEnvelopeToXmlString(envelope);
		return request;
	}
	
	@Override
	public TargetSystemOutputDetails mapJadeResponseToNetsuiteForAddParentCust(String jadeResponse) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		logger.info("***Inside CustomerMappingService mapJadeResponseToNetsuiteForAddParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new ByteArrayInputStream(jadeResponse.getBytes()));
		document.getDocumentElement().normalize();
		XPathExpression result = XPathFactory.newInstance().newXPath()
				.compile("//*[local-name()='addParentResult']/text()");
		targetSystemOutputDetails.setSystemname("JADE");
		targetSystemOutputDetails.setStatus(result.evaluate(document));
		return targetSystemOutputDetails;
	}
	
	//snipp related code
	/*@Override
	public AddParentCustSnippRequest mapUpdParentCustomerToSnipp(UpdParentCustToJADERequest updParentCustToJADERequest) throws JAXBException {
		logger.info("***Inside CustomerMappingService mapUpdParentCustomerToSnipp***");
		AddParentCustSnippRequest addParentCustSnippRequest = new AddParentCustSnippRequest();
		addParentCustSnippRequest.setExtReference(CommonUtil.convertEmptyToNull(String.valueOf(updParentCustToJADERequest.getParentCustData().getNSInternalID())));
		MemberSubType memberSubType = new MemberSubType();
		memberSubType.setName("DD");
		addParentCustSnippRequest.setMemberSubType(memberSubType);
		//addParentCustSnippRequest.setMemberStatus(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getMemberStatus()));
		addParentCustSnippRequest.setMemberStatus("Active");
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setFirstName("-");
		personalDetails.setLastName(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getName()));
		addParentCustSnippRequest.setPersonalDetails(personalDetails);
		List<Address> addressList = new ArrayList<Address>();
		Address address = new Address();
		address.setAddressLine1(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getAddr1()));
		address.setAddressLine2(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getAddr2()));
		address.setCity(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getCity()));
		address.setZip(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getZip()));
		//address.setCountryId(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getSnippCountryCode()));
		address.setCountryId("NZ");
		addressList.add(address);
		addParentCustSnippRequest.setAddress(addressList);
		List<Account> accountList = new ArrayList<Account>();
		Account account = new Account();
		account.setCurrencyId(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getCurrencyId()));
		accountList.add(account);
		addParentCustSnippRequest.setAccount(accountList);
		List<ContactDetail> contactDetailsList = new ArrayList<ContactDetail>();
		ContactDetail contactDetail = new ContactDetail();
		contactDetail.setEmail(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getEmail()));
		contactDetail.setPhone(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getPhone()));
		contactDetail.setMobilePhone(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getMobile()));
		contactDetail.setFax(CommonUtil.convertEmptyToNull(updParentCustToJADERequest.getParentCustData().getFax()));
		contactDetailsList.add(contactDetail);
		addParentCustSnippRequest.setContactDetails(contactDetailsList);
		List<ExtensionDatum> extensionDataList = new ArrayList<ExtensionDatum>();
		ExtensionDatum extensionDatum1 = new ExtensionDatum();
		extensionDatum1.setPropertyName("Book Fair ABCD Marker");
		extensionDatum1.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getBookFairABCDMarker()));
		extensionDataList.add(extensionDatum1);
		ExtensionDatum extensionDatum2 = new ExtensionDatum();
		extensionDatum2.setPropertyName("Decile");
		extensionDatum2.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getDecile()));
		extensionDataList.add(extensionDatum2);
		ExtensionDatum extensionDatum3 = new ExtensionDatum();
		extensionDatum3.setPropertyName("Fair Size");
		extensionDatum3.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getFairSize()));
		extensionDataList.add(extensionDatum3);
		ExtensionDatum extensionDatum4 = new ExtensionDatum();
		extensionDatum4.setPropertyName("Pricing Group");
		extensionDatum4.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getPricingGroup()));
		extensionDataList.add(extensionDatum4);
		ExtensionDatum extensionDatum5 = new ExtensionDatum();
		extensionDatum5.setPropertyName("Region");
		extensionDatum5.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getRegion()));
		extensionDataList.add(extensionDatum5);
		ExtensionDatum extensionDatum6 = new ExtensionDatum();
		extensionDatum6.setPropertyName("Subsidiary");
		extensionDatum6.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getSubsidiary()));
		extensionDataList.add(extensionDatum6);
		ExtensionDatum extensionDatum7 = new ExtensionDatum();
		extensionDatum7.setPropertyName("Zone Number");
		extensionDatum7.setPropertyValue(CommonUtil.convertEmptyToNull(addParentCustToJADERequest.getParentCustData().getZoneNumber()));
		extensionDataList.add(extensionDatum7);
		addParentCustSnippRequest.setExtensionData(extensionDataList);
		return addParentCustSnippRequest;
	}*/
	
	@Override
	public AddParentCustRvaRequest mapUpdParentCustomerToRva(UpdParentCustToJADERequest updParentCustToJADERequest) throws JAXBException {
		logger.info("***Inside CustomerMappingService mapUpdParentCustomerToRva***");
		AddParentCustRvaRequest addParentCustRvaRequest = new AddParentCustRvaRequest();
		addParentCustRvaRequest.setAddress(new AddressRva());
		//address block starts
		addParentCustRvaRequest.getAddress().setAddress1(updParentCustToJADERequest.getParentCustData().getAddr1());
		addParentCustRvaRequest.getAddress().setAddress2(updParentCustToJADERequest.getParentCustData().getAddr2());
		addParentCustRvaRequest.getAddress().setAddress3(updParentCustToJADERequest.getParentCustData().getAddr3());
		addParentCustRvaRequest.getAddress().setCity(updParentCustToJADERequest.getParentCustData().getCity());
		addParentCustRvaRequest.getAddress().setCountry(new CountryRva());
		//country block starts
		addParentCustRvaRequest.getAddress().getCountry().setCreatedDate(updParentCustToJADERequest.getParentCustData().getCountrycreatedate());
		addParentCustRvaRequest.getAddress().getCountry().setCurrency(updParentCustToJADERequest.getParentCustData().getCtrycurrency());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getCtryid())) {
			addParentCustRvaRequest.getAddress().getCountry()
					.setId(Integer.valueOf(updParentCustToJADERequest.getParentCustData().getCtryid()));
		}
		addParentCustRvaRequest.getAddress().getCountry().setIsoCode(updParentCustToJADERequest.getParentCustData().getCtryisoCode());
		addParentCustRvaRequest.getAddress().getCountry().setName(updParentCustToJADERequest.getParentCustData().getCountry());
		addParentCustRvaRequest.getAddress().getCountry().setStatus(updParentCustToJADERequest.getParentCustData().getCtrystatus());
		addParentCustRvaRequest.getAddress().getCountry().setTimeZone(updParentCustToJADERequest.getParentCustData().getCtrytimeZone());
		addParentCustRvaRequest.getAddress().getCountry().setUpdatedDate(updParentCustToJADERequest.getParentCustData().getCtryupdatedDate());
		addParentCustRvaRequest.getAddress().getCountry().setUtc(updParentCustToJADERequest.getParentCustData().getUtc());
		//country block ends
		addParentCustRvaRequest.getAddress().setCreatedDate(updParentCustToJADERequest.getParentCustData().getCreateddate());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getAddid())) {
			addParentCustRvaRequest.getAddress()
					.setId(Integer.valueOf(updParentCustToJADERequest.getParentCustData().getAddid()));
		}
		addParentCustRvaRequest.getAddress().setName(updParentCustToJADERequest.getParentCustData().getAddressee());
		addParentCustRvaRequest.getAddress().setState(updParentCustToJADERequest.getParentCustData().getState());
		addParentCustRvaRequest.getAddress().setStatus(updParentCustToJADERequest.getParentCustData().getStatus());
		addParentCustRvaRequest.getAddress().setUpdatedDate(updParentCustToJADERequest.getParentCustData().getUpdatedDate());
		addParentCustRvaRequest.getAddress().setZipCode(updParentCustToJADERequest.getParentCustData().getZip());
		//address block ends
		addParentCustRvaRequest.setClientId(String.valueOf(updParentCustToJADERequest.getParentCustData().getNSInternalID()));
		addParentCustRvaRequest.setCreatedDate(updParentCustToJADERequest.getParentCustData().getCustcdate());
		addParentCustRvaRequest.setEmail(updParentCustToJADERequest.getParentCustData().getEmail());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getExtaddid())) {
			addParentCustRvaRequest.setExtAddressId(
					Integer.valueOf(updParentCustToJADERequest.getParentCustData().getExtaddid()));
		}
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getId())) {
			addParentCustRvaRequest.setId(Integer.valueOf(updParentCustToJADERequest.getParentCustData().getId()));
		}
		addParentCustRvaRequest.setName(updParentCustToJADERequest.getParentCustData().getName());
		addParentCustRvaRequest.setOrgId(updParentCustToJADERequest.getParentCustData().getOrgId());
		addParentCustRvaRequest.setOrgName(updParentCustToJADERequest.getParentCustData().getOrgName());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getRewardholder())) {
			addParentCustRvaRequest.setRewardHolder(
					Boolean.valueOf(updParentCustToJADERequest.getParentCustData().getRewardholder()));
		}
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getScholasticSubCode())) {
			addParentCustRvaRequest.setScholasticSubCode(
					Integer.valueOf(updParentCustToJADERequest.getParentCustData().getScholasticSubCode()));
		}
		addParentCustRvaRequest.setStatus(updParentCustToJADERequest.getParentCustData().getCuststatus());
		addParentCustRvaRequest.setUpdatedDate(updParentCustToJADERequest.getParentCustData().getCustcdate());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getUserstatus())) {
			addParentCustRvaRequest.setUserStatus(
					Boolean.valueOf(updParentCustToJADERequest.getParentCustData().getUserstatus()));
		}
		addParentCustRvaRequest.setUserType(new UserTypeRva());
		//user type block starts
		addParentCustRvaRequest.getUserType().setCreatedDate(updParentCustToJADERequest.getParentCustData().getUcreatedDate());
		if (!CommonUtil.isNullOrEmpty(updParentCustToJADERequest.getParentCustData().getUid())) {
			addParentCustRvaRequest.getUserType()
					.setId(Integer.valueOf(updParentCustToJADERequest.getParentCustData().getUid()));
		}
		addParentCustRvaRequest.getUserType().setName(updParentCustToJADERequest.getParentCustData().getUname());
		addParentCustRvaRequest.getUserType().setStatus(updParentCustToJADERequest.getParentCustData().getUstatus());
		addParentCustRvaRequest.getUserType().setUpdatedDate(updParentCustToJADERequest.getParentCustData().getUupdatedDate());
		//user type block ends
		return addParentCustRvaRequest;
	}
	
	//snipp related code
	/*@Override
	public TargetSystemOutputDetails mapSnippResponseToNetsuiteForUpdParentCust(AddParentCustSnippResponse snippResponse) {
		logger.info("***Inside CustomerMappingService mapSnippResponseToNetsuiteForUpdParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus(snippResponse.getSuccess());
		targetSystemOutputDetails.setMemberid(snippResponse.getData());
		targetSystemOutputDetails.setErrorresponse(snippResponse.getMessage());
		return targetSystemOutputDetails;
	}*/
	
	@Override
	public TargetSystemOutputDetails mapRvaResponseToNetsuiteForUpdParentCust(AddParentCustRvaSucResponse rvaResponse) {
		logger.info("***Inside CustomerMappingService mapRvaResponseToNetsuiteForUpdParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus("true");
		//targetSystemOutputDetails.setMemberid(snippResponse.getData());
		//targetSystemOutputDetails.setErrorresponse(snippResponse.getMessage());
		return targetSystemOutputDetails;
	}
	
	@Override
	public String mapUpdateParentCustomerToJade(UpdParentCustToJADERequest updParentCustToJADERequest) throws JAXBException {
		logger.info("***Inside CustomerMappingService updParentCustToJADERequest method***");
		String request = "";
		UpdateParent updateParent = new UpdateParent();
		NSIOrganisation nsiOrganisation = new NSIOrganisation();
		nsiOrganisation.setAddr1(updParentCustToJADERequest.getParentCustData().getAddr1());
		nsiOrganisation.setAddr2(updParentCustToJADERequest.getParentCustData().getAddr2());
		nsiOrganisation.setCity(updParentCustToJADERequest.getParentCustData().getCity());
		nsiOrganisation.setCountry(updParentCustToJADERequest.getParentCustData().getCountry());
		nsiOrganisation.setEmail(updParentCustToJADERequest.getParentCustData().getEmail());
		nsiOrganisation.setFax(updParentCustToJADERequest.getParentCustData().getFax());
		nsiOrganisation.setMobile(updParentCustToJADERequest.getParentCustData().getMobile());
		nsiOrganisation.setNSInternalID(updParentCustToJADERequest.getParentCustData().getNSInternalID());
		nsiOrganisation.setName(updParentCustToJADERequest.getParentCustData().getName());
		nsiOrganisation.setPhone(updParentCustToJADERequest.getParentCustData().getPhone());
		nsiOrganisation.setSalesRep(updParentCustToJADERequest.getParentCustData().getSalesRep());
		nsiOrganisation.setState(updParentCustToJADERequest.getParentCustData().getState());
		nsiOrganisation.setTerritory(updParentCustToJADERequest.getParentCustData().getTerritory());
		nsiOrganisation.setUrl(updParentCustToJADERequest.getParentCustData().getUrl());
		nsiOrganisation.setZip(updParentCustToJADERequest.getParentCustData().getZip());
		nsiOrganisation.setExternalId(updParentCustToJADERequest.getParentCustData().getLegacyId());
		updateParent.setOrganisation(nsiOrganisation);
		NetsuiteUpdParentCustEnvelope envelope = new NetsuiteUpdParentCustEnvelope();
		envelope.setBody(new NetsuiteUpdParentCustSoapBody());
		envelope.getBody().setUpdateParent(updateParent);
		request = CommonUtil.convertEnvelopeToXmlString(envelope);
		return request;
	}
	
	@Override
	public TargetSystemOutputDetails mapJadeResponseToNetsuiteForUpdParentCust(String jadeResponse) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		logger.info("***Inside CustomerMappingService mapJadeResponseToNetsuiteForUpdParentCust method***");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new ByteArrayInputStream(jadeResponse.getBytes()));
		document.getDocumentElement().normalize();
		XPathExpression result = XPathFactory.newInstance().newXPath()
				.compile("//*[local-name()='updateParentResult']/text()");
		targetSystemOutputDetails.setSystemname("JADE");
		targetSystemOutputDetails.setStatus(result.evaluate(document));
		return targetSystemOutputDetails;
	}
	
	//snipp related code
	/*@Override
	public TargetSystemOutputDetails mapSnippResponseForException(Exception exception) throws IOException {
		logger.info("****************Inside Snipp Error*****************************");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus("false");
		targetSystemOutputDetails.setErrorresponse("Failed");
		if (exception instanceof CamelExecutionException
				&& exception.getCause() instanceof HttpOperationFailedException) {
			HttpOperationFailedException ex = ((HttpOperationFailedException) exception.getCause());
			AddParentCustSnippResponse addParentCustSnippResponse = CommonUtil
					.getCastObjectFromJsonString(ex.getResponseBody(), AddParentCustSnippResponse.class);
			targetSystemOutputDetails.setStatus(addParentCustSnippResponse.getSuccess());
			targetSystemOutputDetails.setErrorresponse(addParentCustSnippResponse.getMessage());
		}
		return targetSystemOutputDetails;
	}*/
	
	@Override
	public TargetSystemOutputDetails mapRvaResponseForException(Exception exception) throws IOException {
		logger.info("****************Inside Rva Error*****************************");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("SNIPP");
		targetSystemOutputDetails.setStatus("false");
		targetSystemOutputDetails.setErrorresponse("Failed");
		if (exception instanceof CamelExecutionException
				&& exception.getCause() instanceof HttpOperationFailedException) {
			HttpOperationFailedException ex = ((HttpOperationFailedException) exception.getCause());
			String rvaErrorResponse = ex.getResponseBody();
			/*AddParentCustRvaErrorResponse addParentCustRvaErrorResponse = CommonUtil
					.getCastObjectFromJsonString(ex.getResponseBody(), AddParentCustRvaErrorResponse.class);*/
			logger.info("Rva Error Response:-" + rvaErrorResponse);
			targetSystemOutputDetails.setErrorresponse(rvaErrorResponse);
		}
		return targetSystemOutputDetails;
	}
	
	@Override
	public TargetSystemOutputDetails mapJadeResponseForException(Exception exception)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("****************Inside JADE Error*****************************");
		TargetSystemOutputDetails targetSystemOutputDetails = new TargetSystemOutputDetails();
		targetSystemOutputDetails.setSystemname("JADE");
		targetSystemOutputDetails.setStatus("Failed");
		if (exception instanceof CamelExecutionException
				&& exception.getCause() instanceof HttpOperationFailedException) {
			HttpOperationFailedException ex = ((HttpOperationFailedException) exception.getCause());
			String errorResp = ex.getResponseBody();
			DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new ByteArrayInputStream(errorResp.getBytes()));
			document.getDocumentElement().normalize();
			XPathExpression errorTxt = XPathFactory.newInstance().newXPath()
					.compile("//*[local-name()='text']/text()");
			int errorCode = ex.getStatusCode();
			targetSystemOutputDetails.setErrorcode(String.valueOf(errorCode));
			targetSystemOutputDetails.setErrorresponse(errorTxt.evaluate(document));
		}
		return targetSystemOutputDetails;
	}
	
	@Override
	public String updatePalmForInsertCustChild(String status, String message, String jadeId, String internalId)
			throws IOException, ParserConfigurationException, SAXException, DatatypeConfigurationException,
			JAXBException {
		logger.info("***Inside CustomerMappingService updatePalmForInsertCustChild method***");
		String request = "";
		UpdateClient updateClient = new UpdateClient();
		NSIClient nsiClient = new NSIClient();
		nsiClient.setJadeId(jadeId);
		if(internalId!="")
		nsiClient.setNsInternalId(Integer.parseInt(internalId));
		nsiClient.setReason(message);
		nsiClient.setStatus(status);
		updateClient.setNsClient(nsiClient);
			PalmEnvelope palmEnvelope = new PalmEnvelope();
			PalmBody palmBody = new PalmBody();
			palmBody.setupdateClient(updateClient);
			palmEnvelope.setBody(palmBody);
		request = CommonUtil.convertEnvelopeToXmlString(palmEnvelope);
		return request;
	}
	
	@Override
	public String updatePalmForUpdateCustChild(String status, String message,String customerInternalId, String internalId)
			throws IOException, ParserConfigurationException, SAXException, DatatypeConfigurationException,
			JAXBException {
		logger.info("***Inside CustomerMappingService updatePalmForUpdateCustChild method***");
		String request = "";
		UpdateClient updateClient = new UpdateClient();
		NSIClient nsiClient = new NSIClient();
		//nsiClient.setJadeId(customerInternalId);
		nsiClient.setNsInternalId(Integer.parseInt(customerInternalId));
		/*if(internalId!="")
		nsiClient.setNsInternalId(Integer.parseInt(internalId));*/
		nsiClient.setReason(message);
		nsiClient.setStatus(status);
		updateClient.setNsClient(nsiClient);
			PalmEnvelope palmEnvelope = new PalmEnvelope();
			PalmBody palmBody = new PalmBody();
			palmBody.setupdateClient(updateClient);
			palmEnvelope.setBody(palmBody);
		request = CommonUtil.convertEnvelopeToXmlString(palmEnvelope);
		return request;
	}
	
	@Override
	public String mapPropertiesFromFile(final String propertyName) {
		return nzConfigMapper.getPalmItemProps().get(propertyName);
	}
	
	@Override
	public Map<String, Object> getAdditionalParamsForInsertChildCust(InsertChildCustToNSRequest insertChildCustToNSRequest) throws IOException, TransformerException, ParserConfigurationException, SAXException {
		Map<String, Object> additionalParams = new HashMap<>();
		additionalParams.put("WebRequest", CommonUtil.getPrettyPrintStringForXml(insertChildCustToNSRequest));
		additionalParams.put("JadeId", insertChildCustToNSRequest.getExternalId());
		return additionalParams;
	}
	
	@Override
	public Map<String, Object> getAdditionalParamsForUpdateChildCust(UpdateChildCustToNSRequest updateChildCustToNSRequest) throws IOException, TransformerException, ParserConfigurationException, SAXException {
		Map<String, Object> additionalParams = new HashMap<>();
		additionalParams.put("WebRequest", CommonUtil.getPrettyPrintStringForXml(updateChildCustToNSRequest));
		additionalParams.put("CustomerInternalId", updateChildCustToNSRequest.getUpdateChildCustData().getCustomerinternalid());
		return additionalParams;
	}
	
	/**
	 * This method is used for mapping the failed request so that it can be reprocessed.
	 * @param endpoint
	 * @param contentType
	 * @param soapAction
	 * @param request
	 * @return
	 */
	@Override
	public RequestWrapper mapRequestForReprocessing(String endpoint, String contentType, String soapAction,
			String request,String interfaceName) {
		Map<String, Object> additionalParams = new HashMap<>();
		additionalParams.put(CommonUtil.INTERFACE_NAME, interfaceName);
		additionalParams.put(CommonUtil.ENDPOINT, endpoint);
		additionalParams.put(CommonUtil.CONTENT_TYPE, contentType);
		additionalParams.put(CommonUtil.SOAP_ACTION, soapAction);
		RequestWrapper requestWrapper = new RequestWrapper();
		FailedRequest failedRequest = new FailedRequest();
		failedRequest.setRequest(request);
		failedRequest.setResubmissionCount(1);
		requestWrapper.setRequest(failedRequest);
		requestWrapper.setAdditionalParams(additionalParams);
		return requestWrapper;
	}

}
