
package com.scholastic.intl.esb.integration.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "createdDate",
    "updatedDate",
    "status",
    "id",
    "clientId",
    "name",
    "email",
    "rewardHolder",
    "orgId",
    "orgName",
    "userStatus",
    "scholasticSubCode",
    "extAddressId",
    "address",
    "userType"
})
public class AddParentCustRvaSucResponse {

    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("updatedDate")
    private String updatedDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("rewardHolder")
    private Boolean rewardHolder;
    @JsonProperty("orgId")
    private String orgId;
    @JsonProperty("orgName")
    private String orgName;
    @JsonProperty("userStatus")
    private Boolean userStatus;
    @JsonProperty("scholasticSubCode")
    private Integer scholasticSubCode;
    @JsonProperty("extAddressId")
    private Integer extAddressId;
    @JsonProperty("address")
    private AddressRvaSucRes address;
    @JsonProperty("userType")
    private UserTypeRvaSucRes userType;

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("updatedDate")
    public String getUpdatedDate() {
        return updatedDate;
    }

    @JsonProperty("updatedDate")
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("rewardHolder")
    public Boolean getRewardHolder() {
        return rewardHolder;
    }

    @JsonProperty("rewardHolder")
    public void setRewardHolder(Boolean rewardHolder) {
        this.rewardHolder = rewardHolder;
    }

    @JsonProperty("orgId")
    public String getOrgId() {
        return orgId;
    }

    @JsonProperty("orgId")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @JsonProperty("orgName")
    public String getOrgName() {
        return orgName;
    }

    @JsonProperty("orgName")
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonProperty("userStatus")
    public Boolean getUserStatus() {
        return userStatus;
    }

    @JsonProperty("userStatus")
    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    @JsonProperty("scholasticSubCode")
    public Integer getScholasticSubCode() {
        return scholasticSubCode;
    }

    @JsonProperty("scholasticSubCode")
    public void setScholasticSubCode(Integer scholasticSubCode) {
        this.scholasticSubCode = scholasticSubCode;
    }

    @JsonProperty("extAddressId")
    public Integer getExtAddressId() {
        return extAddressId;
    }

    @JsonProperty("extAddressId")
    public void setExtAddressId(Integer extAddressId) {
        this.extAddressId = extAddressId;
    }

    @JsonProperty("address")
    public AddressRvaSucRes getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(AddressRvaSucRes address) {
        this.address = address;
    }

    @JsonProperty("userType")
    public UserTypeRvaSucRes getUserType() {
        return userType;
    }

    @JsonProperty("userType")
    public void setUserType(UserTypeRvaSucRes userType) {
        this.userType = userType;
    }

}
