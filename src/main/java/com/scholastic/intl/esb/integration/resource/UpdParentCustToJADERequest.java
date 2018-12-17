//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.17 at 05:35:46 AM UTC 
//


package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parentCustData" type="{http://resource.integration.esb.intl.scholastic.com}parentCustData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parentCustData"
})
@XmlRootElement(name = "updParentCustToJADERequest")
public class UpdParentCustToJADERequest
    implements Serializable, ToString
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParentCustData parentCustData;

    /**
     * Gets the value of the parentCustData property.
     * 
     * @return
     *     possible object is
     *     {@link ParentCustData }
     *     
     */
    public ParentCustData getParentCustData() {
        return parentCustData;
    }

    /**
     * Sets the value of the parentCustData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentCustData }
     *     
     */
    public void setParentCustData(ParentCustData value) {
        this.parentCustData = value;
    }

    public boolean isSetParentCustData() {
        return (this.parentCustData!= null);
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            ParentCustData theParentCustData;
            theParentCustData = this.getParentCustData();
            strategy.appendField(locator, this, "parentCustData", buffer, theParentCustData);
        }
        return buffer;
    }

}