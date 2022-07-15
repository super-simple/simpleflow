package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.context.SimpleFlowContextFactory;
import com.vzoom.simpleflow.core.factory.SimpleFlowCommonNodeFactory;
import com.vzoom.simpleflow.core.factory.SimpleFlowLineFactory;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowAbstractFlowConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

public class SimpleFlowExecutorFactory {
    public static <C extends SimpleFlowDefaultContext,
            NC extends SimpleFlowDefaultNodeConfig,
            LC extends SimpleFlowDefaultLineConfig,
            FC extends SimpleFlowAbstractFlowConfig<NC, LC>> SimpleFlowExecutor<C, NC, LC, FC>
    build(SimpleFlowCommonNodeFactory<NC, C> nodeFactory,
          SimpleFlowLineFactory<LC, C> lineFactory,
          SimpleFlowContextFactory<C> contextFactory,
          ResultObserver<C, NC, LC, FC> resultObserver) {
        return new SimpleFlowExecutor<>(nodeFactory, lineFactory, contextFactory, resultObserver);
    }
}
