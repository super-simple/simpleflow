package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;
import com.vzoom.simpleflow.core.node.SimpleFlowCommonNode;

public abstract class SimpleFlowAbstractCommonNode<NC extends SimpleFlowDefaultNodeConfig
        , C extends SimpleFlowDefaultContext>
        extends SimpleFlowAbstractComponent<C>
        implements SimpleFlowCommonNode<NC, C> {

    private NC nc;

    @Override
    public NC getConfig() {
        return nc;
    }

    void setConfig(NC nc) {
        this.nc = nc;
    }
}
