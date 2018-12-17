
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scholastic.intl.esb.integration.util.CommonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "createdDate",
    "currency",
    "id",
    "isoCode",
    "name",
    "status",
    "timeZone",
    "updatedDate",
    "utc"
})
public class CountryRva implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("isoCode")
    private String isoCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("timeZone")
    private String timeZone;
    @JsonProperty("updatedDate")
    private String updatedDate;
    @JsonProperty("utc")
    private String utc;

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
    	createdDate = CommonUtil.convertEmptyToNull(createdDate);
        this.createdDate = createdDate;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
    	currency = CommonUtil.convertEmptyToNull(currency);
        this.currency = currency;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("isoCode")
    public String getIsoCode() {
        return isoCode;
    }

    @JsonProperty("isoCode")
    public void setIsoCode(String isoCode) {
    	isoCode = CommonUtil.convertEmptyToNull(isoCode);
        this.isoCode = isoCode;
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

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
    	status = CommonUtil.convertEmptyToNull(status);
        this.status = status;
    }

    @JsonProperty("timeZone")
    public String getTimeZone() {
        return timeZone;
    }

    @JsonProperty("timeZone")
    public void setTimeZone(String timeZone) {
    	timeZone = CommonUtil.convertEmptyToNull(timeZone);
        this.timeZone = timeZone;
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

    @JsonProperty("utc")
    public String getUtc() {
        return utc;
    }

    @JsonProperty("utc")
    public void setUtc(String utc) {
    	utc = CommonUtil.convertEmptyToNull(utc);
        this.utc = utc;
    }

}
