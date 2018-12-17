
package com.scholastic.intl.esb.integration.wsdl.palm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="organisation" type="{urn:JadeWebServices/NetsuiteCustomer/}NSI_Organisation"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "organisation"
})
@XmlRootElement(name = "updateParent")
public class UpdateParent {

    @XmlElement(required = true, nillable = true)
    protected NSIOrganisation organisation;

    /**
     * Gets the value of the organisation property.
     * 
     * @return
     *     possible object is
     *     {@link NSIOrganisation }
     *     
     */
    public NSIOrganisation getOrganisation() {
        return organisation;
    }

    /**
     * Sets the value of the organisation property.
     * 
     * @param value
     *     allowed object is
     *     {@link NSIOrganisation }
     *     
     */
    public void setOrganisation(NSIOrganisation value) {
        this.organisation = value;
    }

}
