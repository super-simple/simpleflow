package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.bo.SimpleFlowBranch;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowAbstractFlowConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleFlowOuterContext<C extends SimpleFlowDefaultContext,
        NC extends SimpleFlowDefaultNodeConfig,
        LC extends SimpleFlowDefaultLineConfig,
        FC extends SimpleFlowAbstractFlowConfig<NC, LC>> {

    private final String id;
    private final Object request;
    private final Map<Integer, Map<String, Map<String, Object>>> contextMap;
    private final Map<Integer, FC> branchMap;
    private final AtomicInteger branchCount = new AtomicInteger(0);
    private Object response;

    SimpleFlowOuterContext(String id, Object request) {
        this.id = id;
        this.request = request;
        this.contextMap = new ConcurrentHashMap<>();
        this.branchMap = new ConcurrentHashMap<>();
    }

    int gainBranchCount() {
        return branchCount.get();
    }

    void increaseBranchCount() {
        branchCount.incrementAndGet();
    }

    void decrementBranchCount() {
        branchCount.decrementAndGet();
    }

    public String getId() {
        return id;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

    void setResponse(Object response) {
        this.response = response;
    }

    public Map<Integer, Map<String, Map<String, Object>>> getContextMap() {
        return contextMap;
    }

    public Map<Integer, FC> getBranchMap() {
        return branchMap;
    }

    void putContextMap(SimpleFlowBranch<C> branch) {
        contextMap.put(branch.getBranchOrder(), branch.getContext().getContextMap());
    }
}
