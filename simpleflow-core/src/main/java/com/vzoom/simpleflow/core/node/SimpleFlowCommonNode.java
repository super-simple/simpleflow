package com.vzoom.simpleflow.core.node;

import com.vzoom.simpleflow.core.SimpleFlowComponent;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

public interface SimpleFlowCommonNode<NC extends SimpleFlowDefaultNodeConfig,
        C extends SimpleFlowDefaultContext> extends SimpleFlowComponent<C> {
    void runNode() throws Exception;

    NC getConfig();

}
