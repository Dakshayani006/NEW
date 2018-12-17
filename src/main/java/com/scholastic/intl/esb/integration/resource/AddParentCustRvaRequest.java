
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scholastic.intl.esb.integration.util.CommonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address",
    "clientId",
    "createdDate",
    "email",
    "extAddressId",
    "id",
    "name",
    "orgId",
    "orgName",
    "rewardHolder",
    "scholasticSubCode",
    "status",
    "updatedDate",
    "userStatus",
    "userType"
})
public class AddParentCustRvaRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("address")
    private AddressRva address;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("email")
    private String email;
    @JsonProperty("extAddressId")
    private Integer extAddressId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("orgId")
    private String orgId;
    @JsonProperty("orgName")
    private String orgName;
    @JsonProperty("rewardHolder")
    private Boolean rewardHolder;
    @JsonProperty("scholasticSubCode")
    private Integer scholasticSubCode;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updatedDate")
    private String updatedDate;
    @JsonProperty("userStatus")
    private Boolean userStatus;
    @JsonProperty("userType")
    private UserTypeRva userType;

    @JsonProperty("address")
    public AddressRva getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(AddressRva address) {
        this.address = address;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
    	clientId = CommonUtil.convertEmptyToNull(clientId);
        this.clientId = clientId;
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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
    	email = CommonUtil.convertEmptyToNull(email);
        this.email = email;
    }

    @JsonProperty("extAddressId")
    public Integer getExtAddressId() {
        return extAddressId;
    }

    @JsonProperty("extAddressId")
    public void setExtAddressId(Integer extAddressId) {
        this.extAddressId = extAddressId;
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

    @JsonProperty("orgId")
    public String getOrgId() {
        return orgId;
    }

    @JsonProperty("orgId")
    public void setOrgId(String orgId) {
    	//orgId = CommonUtil.convertEmptyToNull(orgId);
        this.orgId = orgId;
    }

    @JsonProperty("orgName")
    public String getOrgName() {
        return orgName;
    }

    @JsonProperty("orgName")
    public void setOrgName(String orgName) {
    	//orgName = CommonUtil.convertEmptyToNull(orgName);
        this.orgName = orgName;
    }

    @JsonProperty("rewardHolder")
    public Boolean getRewardHolder() {
        return rewardHolder;
    }

    @JsonProperty("rewardHolder")
    public void setRewardHolder(Boolean rewardHolder) {
        this.rewardHolder = rewardHolder;
    }

    @JsonProperty("scholasticSubCode")
    public Integer getScholasticSubCode() {
        return scholasticSubCode;
    }

    @JsonProperty("scholasticSubCode")
    public void setScholasticSubCode(Integer scholasticSubCode) {
        this.scholasticSubCode = scholasticSubCode;
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

    @JsonProperty("userStatus")
    public Boolean getUserStatus() {
        return userStatus;
    }

    @JsonProperty("userStatus")
    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    @JsonProperty("userType")
    public UserTypeRva getUserType() {
        return userType;
    }

    @JsonProperty("userType")
    public void setUserType(UserTypeRva userType) {
        this.userType = userType;
    }

}
