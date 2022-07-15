package com.vzoom.simpleflow.core.factory;

import com.vzoom.simpleflow.core.SimpleFlowAbstractCommonNode;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

public interface SimpleFlowCommonNodeFactory<NC extends SimpleFlowDefaultNodeConfig, C extends SimpleFlowDefaultContext> {
    SimpleFlowAbstractCommonNode<NC, C> build(String flowCode, String nodeId, NC nodeConfig);
}
