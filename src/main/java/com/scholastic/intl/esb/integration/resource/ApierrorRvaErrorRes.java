
package com.scholastic.intl.esb.integration.resource;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "timestamp",
    "exception",
    "message",
    "debugMessage",
    "elements"
})
public class ApierrorRvaErrorRes {

    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("exception")
    private String exception;
    @JsonProperty("message")
    private String message;
    @JsonProperty("debugMessage")
    private String debugMessage;
    @JsonProperty("elements")
    private List<ElementRvaErrorRes> elements = null;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("exception")
    public String getException() {
        return exception;
    }

    @JsonProperty("exception")
    public void setException(String exception) {
        this.exception = exception;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("debugMessage")
    public String getDebugMessage() {
        return debugMessage;
    }

    @JsonProperty("debugMessage")
    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    @JsonProperty("elements")
    public List<ElementRvaErrorRes> getElements() {
        return elements;
    }

    @JsonProperty("elements")
    public void setElements(List<ElementRvaErrorRes> elements) {
        this.elements = elements;
    }

}
