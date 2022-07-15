package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.flowconfig.SimpleFlowAbstractFlowConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

public interface ResultObserver<C extends SimpleFlowDefaultContext,
        NC extends SimpleFlowDefaultNodeConfig,
        LC extends SimpleFlowDefaultLineConfig,
        FC extends SimpleFlowAbstractFlowConfig<NC, LC>> {
    void notifyResult(SimpleFlowInnerContext<C, NC, LC, FC> innerContext) throws Exception;
}
