package com.vzoom.simpleflow.core.flowconfig;

public class SimpleFlowDefaultLineConfig extends SimpleFlowAbstractComponentConfig implements SimpleFlowLineConfig {

    protected String fromId;
    protected String toId;

    @Override
    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    @Override
    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
