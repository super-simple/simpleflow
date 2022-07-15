package com.vzoom.simpleflow.core.flowconfig;

import com.vzoom.simpleflow.core.constant.SimpleFlowEventAction;
import com.vzoom.simpleflow.core.constant.SimpleFlowNodeType;

public class SimpleFlowDefaultNodeConfig extends SimpleFlowAbstractComponentConfig implements SimpleFlowNodeConfig {

    protected String code;
    protected SimpleFlowNodeType type;
    protected SimpleFlowEventAction eventAction;

    @Override
    public SimpleFlowNodeType getType() {
        return type;
    }

    public void setType(SimpleFlowNodeType type) {
        this.type = type;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public SimpleFlowEventAction getEventAction() {
        return eventAction;
    }

    public void setEventAction(SimpleFlowEventAction eventAction) {
        this.eventAction = eventAction;
    }


}
