package com.vzoom.simpleflow.core.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzoom.simpleflow.core.context.SimpleFlowContext;

public class SimpleFlowResp {
    private String code;
    private String message;
    private SimpleFlowContext context;
    @JsonIgnore
    private Exception exception;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleFlowContext getContext() {
        return context;
    }

    public void setContext(SimpleFlowContext context) {
        this.context = context;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
