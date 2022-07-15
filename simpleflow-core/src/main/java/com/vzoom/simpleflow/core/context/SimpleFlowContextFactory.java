package com.vzoom.simpleflow.core.context;

import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;

public interface SimpleFlowContextFactory<C extends SimpleFlowDefaultContext> {

    C build();
}
