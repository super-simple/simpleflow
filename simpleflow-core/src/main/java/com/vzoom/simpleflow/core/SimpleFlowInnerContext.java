package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.bo.SimpleFlowBranch;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowAbstractFlowConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowGraphConfig;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleFlowInnerContext<C extends SimpleFlowDefaultContext,
        NC extends SimpleFlowDefaultNodeConfig,
        LC extends SimpleFlowDefaultLineConfig,
        FC extends SimpleFlowAbstractFlowConfig<NC, LC>> {

    private final String id;
    private final FC fc;
    private final Object request;
    private final Map<Integer, Map<String, Map<String, Object>>> contextMap;
    private final Map<String, List<LC>> lineListMap;
    private final Map<String, NC> nodeConfigMap;
    private final Map<Integer, FC> branchMap;
    private final AtomicInteger branchCount = new AtomicInteger(0);
    private Object response;

    SimpleFlowInnerContext(String id, FC fc, Object request) {
        this.id = id;
        this.fc = fc;
        this.request = request;
        this.contextMap = new ConcurrentHashMap<>();
        this.lineListMap = toLineListMap(fc);
        this.nodeConfigMap = toNodeConfigMap(fc);
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

    public FC getFc() {
        return fc;
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

    Map<String, List<LC>> getLineListMap() {
        return lineListMap;
    }

    public Map<String, NC> getNodeConfigMap() {
        return nodeConfigMap;
    }

    public Map<Integer, FC> getBranchMap() {
        return branchMap;
    }

    void putContextMap(SimpleFlowBranch<C> branch) {
        contextMap.put(branch.getBranchOrder(), branch.getContext().getContextMap());
    }

    Map<String, List<LC>> toLineListMap(FC fc) {
        Map<String, SimpleFlowGraphConfig<NC, LC>> flowDefine = fc.getFlowDefine();
        Collection<SimpleFlowGraphConfig<NC, LC>> values = flowDefine.values();
        Map<String, List<LC>> result = new HashMap<>();
        for (SimpleFlowGraphConfig<NC, LC> value : values) {
            TreeSet<LC> lineList = value.getLineList();
            for (LC lineConfig : lineList) {
                String fromId = lineConfig.getFromId();
                List<LC> list = result.computeIfAbsent(fromId, k -> new ArrayList<>());
                list.add(lineConfig);
            }
        }
        return result;
    }

    Map<String, NC> toNodeConfigMap(FC fc) {
        Map<String, SimpleFlowGraphConfig<NC, LC>> flowDefine = fc.getFlowDefine();
        Collection<SimpleFlowGraphConfig<NC, LC>> values = flowDefine.values();
        Map<String, NC> result = new HashMap<>();
        for (SimpleFlowGraphConfig<NC, LC> value : values) {
            TreeSet<NC> nodeList = value.getNodeList();
            for (NC nodeConfig : nodeList) {
                result.put(nodeConfig.getId(), nodeConfig);
            }
        }
        return result;
    }
}
