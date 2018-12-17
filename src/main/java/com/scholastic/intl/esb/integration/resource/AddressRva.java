
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scholastic.intl.esb.integration.util.CommonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address1",
    "address2",
    "address3",
    "city",
    "country",
    "createdDate",
    "id",
    "name",
    "state",
    "status",
    "updatedDate",
    "zipCode"
})
public class AddressRva implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("address3")
    private String address3;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private CountryRva country;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("state")
    private String state;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updatedDate")
    private String updatedDate;
    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("address1")
    public String getAddress1() {
        return address1;
    }

    @JsonProperty("address1")
    public void setAddress1(String address1) {
    	address1 = CommonUtil.convertEmptyToNull(address1);
        this.address1 = address1;
    }

    @JsonProperty("address2")
    public String getAddress2() {
        return address2;
    }

    @JsonProperty("address2")
    public void setAddress2(String address2) {
    	address2 = CommonUtil.convertEmptyToNull(address2);
        this.address2 = address2;
    }

    @JsonProperty("address3")
    public String getAddress3() {
        return address3;
    }

    @JsonProperty("address3")
    public void setAddress3(String address3) {
    	address3 = CommonUtil.convertEmptyToNull(address3);
        this.address3 = address3;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
    	city = CommonUtil.convertEmptyToNull(city);
        this.city = city;
    }

    @JsonProperty("country")
    public CountryRva getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(CountryRva country) {
        this.country = country;
    }

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
    	createdDate = CommonUtil.convertEmptyToNull(createdDate);
        this.createdDate = createdDate;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
    	name = CommonUtil.convertEmptyToNull(name);
        this.name = name;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
    	state = CommonUtil.convertEmptyToNull(state);
        this.state = state;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
    	status = CommonUtil.convertEmptyToNull(status);
        this.status = status;
    }

    @JsonProperty("updatedDate")
    public String getUpdatedDate() {
        return updatedDate;
    }

    @JsonProperty("updatedDate")
    public void setUpdatedDate(String updatedDate) {
    	updatedDate = CommonUtil.convertEmptyToNull(updatedDate);
        this.updatedDate = updatedDate;
    }

    @JsonProperty("zipCode")
    public String getZipCode() {
        return zipCode;
    }

    @JsonProperty("zipCode")
    public void setZipCode(String zipCode) {
    	zipCode = CommonUtil.convertEmptyToNull(zipCode);
        this.zipCode = zipCode;
    }

}
