
package com.scholastic.intl.esb.integration.wsdl.palm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NSI_Client complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NSI_Client"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:JadeWebServices/NetsuiteCustomer/}Provider"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="jadeId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nsInternalId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NSI_Client", propOrder = {
    "jadeId",
    "nsInternalId",
    "reason",
    "status"
})
public class NSIClient
    extends Provider
{

    @XmlElement(required = true)
    protected String jadeId;
    protected Integer nsInternalId;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the jadeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJadeId() {
        return jadeId;
    }

    /**
     * Sets the value of the jadeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJadeId(String value) {
        this.jadeId = value;
    }

    /**
     * Gets the value of the nsInternalId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNsInternalId() {
        return nsInternalId;
    }

    /**
     * Sets the value of the nsInternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNsInternalId(Integer value) {
        this.nsInternalId = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
