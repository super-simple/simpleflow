package com.vzoom.simpleflow.core.flowconfig;

import java.util.Map;

public abstract class SimpleFlowAbstractFlowConfig<N extends SimpleFlowDefaultNodeConfig, L extends SimpleFlowDefaultLineConfig> {
    private String flowCode;
    private Map<String, SimpleFlowGraphConfig<N, L>> flowDefine;

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public Map<String, SimpleFlowGraphConfig<N, L>> getFlowDefine() {
        return flowDefine;
    }

    public void setFlowDefine(Map<String, SimpleFlowGraphConfig<N, L>> flowDefine) {
        this.flowDefine = flowDefine;
    }
}
