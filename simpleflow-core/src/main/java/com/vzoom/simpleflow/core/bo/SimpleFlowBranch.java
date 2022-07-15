package com.vzoom.simpleflow.core.bo;

import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.exceptionclz.SimpleFlowConfigException;

import java.util.HashSet;
import java.util.Set;

public class SimpleFlowBranch<C extends SimpleFlowDefaultContext> {
    private final int branchOrder;
    private final C context;
    private final Set<String> subFlowSet;
    private String currentSubFlow;
    private String errorMsg;
    private Exception originException;
    private Exception secondException;
    private SimpleFlowConfigException configException;

    public SimpleFlowBranch(int branchOrder, C context) {
        this.branchOrder = branchOrder;
        this.context = context;
        this.subFlowSet = new HashSet<>();
    }

    public String getCurrentSubFlow() {
        return currentSubFlow;
    }

    public void setCurrentSubFlow(String currentSubFlow) {
        this.currentSubFlow = currentSubFlow;
    }

    public Set<String> getSubFlowSet() {
        return subFlowSet;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getBranchOrder() {
        return branchOrder;
    }

    public C getContext() {
        return context;
    }

    public Exception getOriginException() {
        return originException;
    }

    public void setOriginException(Exception originException) {
        this.originException = originException;
    }

    public Exception getSecondException() {
        return secondException;
    }

    public void setSecondException(Exception secondException) {
        this.secondException = secondException;
    }

    public SimpleFlowConfigException getConfigException() {
        return configException;
    }

    public void setConfigException(SimpleFlowConfigException configException) {
        this.configException = configException;
    }

    @Override
    public String toString() {
        return Integer.toString(branchOrder);
    }
}
