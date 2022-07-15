package com.vzoom.simpleflow.core.context;

import com.vzoom.simpleflow.core.DeepClone;

public interface SimpleFlowContext extends DeepClone {
    Object getRequest();

    void putVariable(String variableName, Object value);

    <T> T getVariable(String variableName);

    <T> void setResponse(T response);
}
