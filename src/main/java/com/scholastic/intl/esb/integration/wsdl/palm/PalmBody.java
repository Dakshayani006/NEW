
package com.scholastic.intl.esb.integration.wsdl.palm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.scholastic.intl.esb.integration.wsdl.palm.UpdateClient;


/**
 * <p>Java class for UpdateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="record" type="{urn:core_2016_1.platform.webservices.netsuite.com}Record"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Body", namespace = "urn:messages_2015_1.platform.webservices.netsuite.com", propOrder = {
    "updateClient"
})
public class PalmBody {

	 @XmlElement(required = true)
	 protected UpdateClient updateClient;

	public UpdateClient getupdateClient() {
		return updateClient;
	}

	public void setupdateClient(UpdateClient updateClient) {
		this.updateClient = updateClient;
	}

	
}
