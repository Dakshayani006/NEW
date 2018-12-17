package com.scholastic.intl.esb.integration.util;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {
	private Map<String, String> namespaceMap = new HashMap<>();

	/**
	 * Create mappings.
	 */
	public DefaultNamespacePrefixMapper() {
		namespaceMap.put("http://schemas.xmlsoap.org/soap/envelope/", "soapenv");
		namespaceMap.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		namespaceMap.put("urn:JadeWebServices/NetsuiteCustomer/", "net");
	}

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		// TODO Auto-generated method stub
		return namespaceMap.getOrDefault(namespaceUri, suggestion);
	}

}
