package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.line.SimpleFlowLine;

public abstract class SimpleFlowAbstractLine<LC extends SimpleFlowDefaultLineConfig,
        C extends SimpleFlowDefaultContext> extends SimpleFlowAbstractComponent<C>
        implements SimpleFlowLine<LC, C> {

    private LC lc;

    @Override
    public LC getConfig() {
        return null;
    }

    void setConfig(LC lc) {
        this.lc = lc;
    }
}
