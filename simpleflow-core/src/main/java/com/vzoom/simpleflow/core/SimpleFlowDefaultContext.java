package com.vzoom.simpleflow.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vzoom.simpleflow.core.constant.SimpleFlowContextName;
import com.vzoom.simpleflow.core.context.SimpleFlowContext;
import com.vzoom.simpleflow.core.util.Clones;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleFlowDefaultContext implements SimpleFlowContext {

    private final Map<String, Map<String, Object>> contextMap;
    private Object request;
    private Object response;

    protected SimpleFlowDefaultContext() {
        this.contextMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object getRequest() {
        return request;
    }

    void setRequest(Object request) {
        this.request = request;
    }

    @Override
    public void putVariable(String variableName, Object value) {
        Map<String, Object> defaultMap = contextMap.computeIfAbsent(SimpleFlowContextName.DEFAULT, k -> new HashMap<>());
        defaultMap.put(variableName, value);
    }

    @Override
    public <T> T getVariable(String variableName) {
        Map<String, Object> defaultMap = contextMap.get(SimpleFlowContextName.DEFAULT);
        if (defaultMap == null) {
            return null;
        }
        return (T) defaultMap.get(variableName);
    }

    Object getResponse() {
        return response;
    }

    @Override
    public <T> void setResponse(T response) {
        this.response = response;
    }

    Map<String, Map<String, Object>> getContextMap() {
        return contextMap;
    }

    @Override
    public SimpleFlowDefaultContext deepClone() {
        return Clones.cloneObject(this, new TypeReference<SimpleFlowDefaultContext>() {
        });
    }
}
