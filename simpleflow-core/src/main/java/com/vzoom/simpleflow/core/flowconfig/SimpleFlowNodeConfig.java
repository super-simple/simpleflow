package com.vzoom.simpleflow.core.flowconfig;


import com.vzoom.simpleflow.core.constant.SimpleFlowEventAction;
import com.vzoom.simpleflow.core.constant.SimpleFlowNodeType;

public interface SimpleFlowNodeConfig extends SimpleFlowComponentConfig {
    SimpleFlowNodeType getType();

    String getCode();

    SimpleFlowEventAction getEventAction();
}
