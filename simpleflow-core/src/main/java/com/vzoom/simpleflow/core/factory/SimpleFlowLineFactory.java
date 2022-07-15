package com.vzoom.simpleflow.core.factory;

import com.vzoom.simpleflow.core.SimpleFlowAbstractLine;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;

public interface SimpleFlowLineFactory<LC extends SimpleFlowDefaultLineConfig, C extends SimpleFlowDefaultContext> {
    SimpleFlowAbstractLine<LC, C> build(String flowCode, String nodeId, LC nodeConfig);
}
