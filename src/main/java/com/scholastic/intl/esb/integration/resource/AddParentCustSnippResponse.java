package com.scholastic.intl.esb.integration.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"Success",
"Message",
"Data"
})
public class AddParentCustSnippResponse {

@JsonProperty("Success")
private String success;
@JsonProperty("Message")
private String message;
@JsonProperty("Data")
private String data;

@JsonProperty("Success")
public String getSuccess() {
return success;
}

@JsonProperty("Success")
public void setSuccess(String success) {
this.success = success;
}

@JsonProperty("Message")
public String getMessage() {
return message;
}

@JsonProperty("Message")
public void setMessage(String message) {
this.message = message;
}

@JsonProperty("Data")
public String getData() {
return data;
}

@JsonProperty("Data")
public void setData(String data) {
this.data = data;
}

}