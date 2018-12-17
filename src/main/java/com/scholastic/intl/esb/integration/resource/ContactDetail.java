
package com.scholastic.intl.esb.integration.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Email",
    "Phone",
    "MobilePhone",
    "Fax"
})
public class ContactDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("Email")
    private String email;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("Email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("Phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("Phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("MobilePhone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    @JsonProperty("MobilePhone")
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @JsonProperty("Fax")
    public String getFax() {
        return fax;
    }

    @JsonProperty("Fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

}
