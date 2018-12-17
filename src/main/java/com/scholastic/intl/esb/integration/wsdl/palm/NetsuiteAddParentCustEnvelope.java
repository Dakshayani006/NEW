
package com.scholastic.intl.esb.integration.wsdl.palm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
@XmlType(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/", propOrder = {"body"})
@XmlRootElement(name = "Envelope",namespace="http://schemas.xmlsoap.org/soap/envelope/")
public class NetsuiteAddParentCustEnvelope {

    /*@XmlElement(required = true,name="Header")
    protected Header header;*/
    @XmlElement(required = true,name="Body")
    protected NetsuiteAddParentCustSoapBody body;
	/*public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}*/
	public NetsuiteAddParentCustSoapBody getBody() {
		return body;
	}
	public void setBody(NetsuiteAddParentCustSoapBody body) {
		this.body = body;
	}
}
