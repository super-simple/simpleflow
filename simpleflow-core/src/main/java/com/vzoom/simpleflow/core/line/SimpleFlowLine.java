package com.vzoom.simpleflow.core.line;

import com.vzoom.simpleflow.core.SimpleFlowComponent;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;

public interface SimpleFlowLine<LC extends SimpleFlowDefaultLineConfig,
        C extends SimpleFlowDefaultContext> extends SimpleFlowComponent<C> {
    boolean runLine() throws Exception;

    LC getConfig();
}
