
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AddressLine1",
    "AddressLine2",
    "City",
    "Zip",
    "CountryId"
})
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("AddressLine1")
    private String addressLine1;
    @JsonProperty("AddressLine2")
    private String addressLine2;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Zip")
    private String zip;
    @JsonProperty("CountryId")
    private String countryId;

    @JsonProperty("AddressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    @JsonProperty("AddressLine1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @JsonProperty("AddressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    @JsonProperty("AddressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty("City")
    public String getCity() {
        return city;
    }

    @JsonProperty("City")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("Zip")
    public String getZip() {
        return zip;
    }

    @JsonProperty("Zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @JsonProperty("CountryId")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("CountryId")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
