
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ExtReference",
    "MemberSubType",
    "MemberStatus",
    "PersonalDetails",
    "Address",
    "ContactDetails",
})
public class AddParentCustSnippRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("ExtReference")
    private String extReference;
    @JsonProperty("MemberSubType")
    private MemberSubType memberSubType;
    @JsonProperty("MemberStatus")
    private String memberStatus;
    @JsonProperty("PersonalDetails")
    private PersonalDetails personalDetails;
    @JsonProperty("Address")
    private List<Address> address = null;
    @JsonProperty("ContactDetails")
    private List<ContactDetail> contactDetails = null;

    @JsonProperty("ExtReference")
    public String getExtReference() {
        return extReference;
    }

    @JsonProperty("ExtReference")
    public void setExtReference(String extReference) {
        this.extReference = extReference;
    }

    @JsonProperty("MemberSubType")
    public MemberSubType getMemberSubType() {
        return memberSubType;
    }

    @JsonProperty("MemberSubType")
    public void setMemberSubType(MemberSubType memberSubType) {
        this.memberSubType = memberSubType;
    }

    @JsonProperty("MemberStatus")
    public String getMemberStatus() {
        return memberStatus;
    }

    @JsonProperty("MemberStatus")
    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    @JsonProperty("PersonalDetails")
    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    @JsonProperty("PersonalDetails")
    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    @JsonProperty("Address")
    public List<Address> getAddress() {
        return address;
    }

    @JsonProperty("Address")
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @JsonProperty("ContactDetails")
    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    @JsonProperty("ContactDetails")
    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

}
