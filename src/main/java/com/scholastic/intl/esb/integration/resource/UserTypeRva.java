
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scholastic.intl.esb.integration.util.CommonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "createdDate",
    "id",
    "name",
    "status",
    "updatedDate"
})
public class UserTypeRva implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updatedDate")
    private String updatedDate;

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

}
