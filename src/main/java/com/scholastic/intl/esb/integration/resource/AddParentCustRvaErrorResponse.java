
package com.scholastic.intl.esb.integration.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apierror"
})
public class AddParentCustRvaErrorResponse {

    @JsonProperty("apierror")
    private ApierrorRvaErrorRes apierror;

    @JsonProperty("apierror")
    public ApierrorRvaErrorRes getApierror() {
        return apierror;
    }

    @JsonProperty("apierror")
    public void setApierror(ApierrorRvaErrorRes apierror) {
        this.apierror = apierror;
    }

}
