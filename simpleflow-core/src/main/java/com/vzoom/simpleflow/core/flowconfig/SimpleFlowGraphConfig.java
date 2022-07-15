package com.vzoom.simpleflow.core.flowconfig;

import java.util.TreeSet;

public class SimpleFlowGraphConfig<N extends SimpleFlowNodeConfig, L extends SimpleFlowLineConfig> {
    private TreeSet<N> nodeList;
    private TreeSet<L> lineList;

    public TreeSet<N> getNodeList() {
        return nodeList;
    }

    public void setNodeList(TreeSet<N> nodeList) {
        this.nodeList = nodeList;
    }

    public TreeSet<L> getLineList() {
        return lineList;
    }

    public void setLineList(TreeSet<L> lineList) {
        this.lineList = lineList;
    }
}
