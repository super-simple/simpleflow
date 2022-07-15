package com.vzoom.simpleflow.core.bo;

import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

public class SimpleFlowEvent<N extends SimpleFlowDefaultNodeConfig> {
    private N nodeConfig;
    private String eventCode;

    public N getNodeConfig() {
        return nodeConfig;
    }

    public void setNodeConfig(N nodeConfig) {
        this.nodeConfig = nodeConfig;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}
