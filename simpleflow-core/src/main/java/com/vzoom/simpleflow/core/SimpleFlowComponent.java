package com.vzoom.simpleflow.core;

public interface SimpleFlowComponent<C extends SimpleFlowDefaultContext> {
    String getId();

    C getContext();

}
