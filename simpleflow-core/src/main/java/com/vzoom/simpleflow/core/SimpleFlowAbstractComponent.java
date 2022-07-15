package com.vzoom.simpleflow.core;

public abstract class SimpleFlowAbstractComponent<C extends SimpleFlowDefaultContext> implements SimpleFlowComponent<C> {
    private String id;

    private C context;

    @Override
    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public C getContext() {
        return context;
    }

    void setContext(C context) {
        this.context = context;
    }

}
