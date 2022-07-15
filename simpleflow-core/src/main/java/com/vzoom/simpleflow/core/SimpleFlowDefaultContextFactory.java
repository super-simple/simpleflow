package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.context.SimpleFlowContextFactory;

public class SimpleFlowDefaultContextFactory implements
        SimpleFlowContextFactory<SimpleFlowDefaultContext> {
    @Override
    public SimpleFlowDefaultContext build() {
        return new SimpleFlowDefaultContext();
    }
}
