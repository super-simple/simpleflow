package com.vzoom.simpleflow.core;

import com.vzoom.simpleflow.core.bo.SimpleFlowBranch;
import com.vzoom.simpleflow.core.bo.SimpleFlowEvent;
import com.vzoom.simpleflow.core.constant.SimpleFlowEventAction;
import com.vzoom.simpleflow.core.constant.SimpleFlowGivenEvent;
import com.vzoom.simpleflow.core.constant.SimpleFlowNodeType;
import com.vzoom.simpleflow.core.context.SimpleFlowContextFactory;
import com.vzoom.simpleflow.core.exceptionclz.SimpleFlowConfigException;
import com.vzoom.simpleflow.core.factory.SimpleFlowCommonNodeFactory;
import com.vzoom.simpleflow.core.factory.SimpleFlowLineFactory;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowAbstractFlowConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultLineConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowDefaultNodeConfig;
import com.vzoom.simpleflow.core.flowconfig.SimpleFlowGraphConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleFlowExecutor<
        C extends SimpleFlowDefaultContext,
        NC extends SimpleFlowDefaultNodeConfig,
        LC extends SimpleFlowDefaultLineConfig,
        FC extends SimpleFlowAbstractFlowConfig<NC, LC>> {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleFlowExecutor.class);

    private final ExecutorService businessThreadPool = Executors.newCachedThreadPool();
    private final SimpleFlowCommonNodeFactory<NC, C> nodeFactory;
    private final SimpleFlowLineFactory<LC, C> lineFactory;
    private final SimpleFlowContextFactory<C> contextFactory;
    private final ResultObserver<C, NC, LC, FC> resultObserver;

    SimpleFlowExecutor(SimpleFlowCommonNodeFactory<NC, C> nodeFactory,
                       SimpleFlowLineFactory<LC, C> lineFactory,
                       SimpleFlowContextFactory<C> contextFactory,
                       ResultObserver<C, NC, LC, FC> resultObserver) {
        this.nodeFactory = nodeFactory;
        this.lineFactory = lineFactory;
        this.contextFactory = contextFactory;
        this.resultObserver = resultObserver;
    }

    public void runFlow(String id, FC fc, Object request) {
        Map<String, SimpleFlowGraphConfig<NC, LC>> flowDefine = fc.getFlowDefine();
        SimpleFlowGraphConfig<NC, LC> startSubFlow = flowDefine.get(SimpleFlowGivenEvent.START);
        if (startSubFlow == null) {
            throw new SimpleFlowConfigException("no start subflow");
        }
        TreeSet<NC> nodeList = startSubFlow.getNodeList();
        NC startEventConfig = null;
        for (NC NC : nodeList) {
            if (NC.getType() == SimpleFlowNodeType.EVENT && NC.getEventAction() == SimpleFlowEventAction.CATCH) {
                startEventConfig = NC;
            }
        }
        if (startEventConfig == null) {
            throw new SimpleFlowConfigException("no start event");
        }

        SimpleFlowEvent<NC> event = new SimpleFlowEvent<>();
        event.setEventCode(SimpleFlowGivenEvent.START);
        event.setNodeConfig(startEventConfig);
        C context = contextFactory.build();
        context.setRequest(request);
        SimpleFlowBranch<C> firstBranch = new SimpleFlowBranch<>(1, context);
        SimpleFlowInnerContext<C, NC, LC, FC> innerContext = new SimpleFlowInnerContext<>(id, fc, request);
        innerContext.putContextMap(firstBranch);
        throwEvent(event, innerContext, firstBranch);
    }

    private void throwEvent(SimpleFlowEvent<NC> throwEvent, SimpleFlowInnerContext<C, NC, LC, FC> innerContext, SimpleFlowBranch<C> branch) {
        int branchOrder = branch.getBranchOrder();
        if (branchOrder != 1) {
            String eventCode = throwEvent.getEventCode();
            if (SimpleFlowGivenEvent.START.compareTo(eventCode) == 0) {
                throw new SimpleFlowConfigException("can not throw start event");
            }
        }
        catchEvent(throwEvent, innerContext, branch);
    }

    private void catchEvent(SimpleFlowEvent<NC> catchEvent, SimpleFlowInnerContext<C, NC, LC, FC> innerContext, SimpleFlowBranch<C> branch) {
        businessThreadPool.submit(() -> {
            try {
                doCatchEvent(catchEvent, innerContext, branch);
            } catch (Exception e) {
                LOG.error("simple flow bug", e);
            }
        });
    }

    private void doCatchEvent(SimpleFlowEvent<NC> catchEvent, SimpleFlowInnerContext<C, NC, LC, FC> innerContext, SimpleFlowBranch<C> branch) {
        String eventCode = catchEvent.getEventCode();
        Set<String> subFlowSet = branch.getSubFlowSet();
        switch (eventCode) {
            case SimpleFlowGivenEvent.START: {
                innerContext.increaseBranchCount();
                if (subFlowSet.contains(SimpleFlowGivenEvent.START)) {
                    branch.setErrorMsg("infinite loop of event [" + eventCode + ']');
                    return;
                }
                subFlowSet.add(SimpleFlowGivenEvent.START);
                branch.setCurrentSubFlow(SimpleFlowGivenEvent.START);

                iterateFlow(catchEvent, innerContext, branch);
                break;
            }
            case SimpleFlowGivenEvent.ERROR: {
                String errorMsg = branch.getErrorMsg();
                SimpleFlowConfigException configException = branch.getConfigException();
                Exception secondException = branch.getSecondException();
                if (errorMsg != null || configException != null || secondException != null) {
                    notifyResult(innerContext);
                } else {
                    FC fc = innerContext.getFc();
                    Map<String, SimpleFlowGraphConfig<NC, LC>> flowDefine = fc.getFlowDefine();
                    SimpleFlowGraphConfig<NC, LC> errSubFlow = flowDefine.get(SimpleFlowGivenEvent.ERROR);
                    if (errSubFlow != null) {
                        if (subFlowSet.contains(SimpleFlowGivenEvent.ERROR)) {
                            branch.setErrorMsg("infinite loop of event [" + eventCode + ']');
                            return;
                        }
                        subFlowSet.add(SimpleFlowGivenEvent.ERROR);
                        branch.setCurrentSubFlow(SimpleFlowGivenEvent.ERROR);

                        NC catchNodeConfig = findCatchNodeConfig(errSubFlow);
                        catchEvent.setNodeConfig(catchNodeConfig);
                        iterateFlow(catchEvent, innerContext, branch);
                    } else {
                        notifyResult(innerContext);
                    }
                }
                break;
            }
            case SimpleFlowGivenEvent.END: {
                notifyResult(innerContext);
                break;
            }
            case SimpleFlowGivenEvent.SPLIT: {
                innerContext.increaseBranchCount();
                iterateFlow(catchEvent, innerContext, branch);
                break;
            }
            case SimpleFlowGivenEvent.AGG: {
                break;
            }
            default: {
                FC fc = innerContext.getFc();
                Map<String, SimpleFlowGraphConfig<NC, LC>> flowDefine = fc.getFlowDefine();
                SimpleFlowGraphConfig<NC, LC> subFLow = flowDefine.get(eventCode);
                if (subFLow != null) {
                    if (subFlowSet.contains(eventCode)) {
                        branch.setErrorMsg("infinite loop of event [" + eventCode + ']');
                        return;
                    }
                    subFlowSet.add(eventCode);
                    branch.setCurrentSubFlow(eventCode);

                    NC catchNodeConfig = findCatchNodeConfig(subFLow);
                    catchEvent.setNodeConfig(catchNodeConfig);
                    iterateFlow(catchEvent, innerContext, branch);
                } else {
                    branch.setErrorMsg("no such flow [" + eventCode + ']');
                }
            }
        }
    }

    private void iterateFlow(SimpleFlowEvent<NC> event, SimpleFlowInnerContext<C, NC, LC, FC> innerContext, SimpleFlowBranch<C> branch) {
        try {
            NC nodeConfig = event.getNodeConfig();
            doIterateFlow(nodeConfig, innerContext, branch);
        } catch (Exception e) {
            if (e instanceof SimpleFlowConfigException) {
                branch.setConfigException((SimpleFlowConfigException) e);
            } else {
                Exception originException = branch.getOriginException();
                if (originException == null) {
                    branch.setOriginException(e);
                } else {
                    branch.setSecondException(e);
                }
            }
            SimpleFlowEvent<NC> errorEvent = new SimpleFlowEvent<>();
            errorEvent.setEventCode(SimpleFlowGivenEvent.ERROR);
            throwEvent(errorEvent, innerContext, branch);
        }
    }

    private void doIterateFlow(NC nodeConfig, SimpleFlowInnerContext<C, NC, LC, FC> innerContext, SimpleFlowBranch<C> branch) throws Exception {
        String nodeId = nodeConfig.getId();
        SimpleFlowNodeType type = nodeConfig.getType();
        SimpleFlowEventAction eventAction = nodeConfig.getEventAction();
        FC fc = innerContext.getFc();
        String flowCode = fc.getFlowCode();
        C context = branch.getContext();
        if (type == SimpleFlowNodeType.NODE ||
                (type == SimpleFlowNodeType.EVENT && eventAction == SimpleFlowEventAction.CATCH)) {
            if (type == SimpleFlowNodeType.NODE) {
                SimpleFlowAbstractCommonNode<NC, C> commonNode = nodeFactory.build(flowCode, nodeId, nodeConfig);
                commonNode.setId(nodeId);
                commonNode.setContext(context);
                commonNode.setConfig(nodeConfig);
                commonNode.runNode();
                assignResponse(innerContext, context);
            }
            Map<String, List<LC>> lineListMap = innerContext.getLineListMap();
            List<LC> lineConfigList = lineListMap.get(nodeId);
            if (lineConfigList == null) {
                throw new SimpleFlowConfigException('[' + nodeId + ']' + "no corresponding lineConfigList");
            }
            int splitCount = 0;
            int lineConfigListSize = lineConfigList.size();
            C cloneContext = null;
            if (lineConfigListSize != 1) {
                cloneContext = (C) context.deepClone();
                innerContext.decrementBranchCount();
            }
            for (LC lineConfig : lineConfigList) {
                String lineId = lineConfig.getId();
                SimpleFlowAbstractLine<LC, C> line = lineFactory.build(flowCode, lineId, lineConfig);
                line.setId(lineId);
                if (lineConfigListSize == 1) {
                    line.setContext(context);
                } else {
                    if (splitCount == 0) {
                        line.setContext(context);
                    } else {
                        C splitContext = (C) cloneContext.deepClone();
                        splitContext.setRequest(innerContext.getRequest());
                        line.setContext(splitContext);
                    }
                }
                line.setConfig(lineConfig);
                boolean isRunNext = line.runLine();
                assignResponse(innerContext, context);
                if (isRunNext) {
                    String toId = lineConfig.getToId();
                    Map<String, NC> nodeConfigMap = innerContext.getNodeConfigMap();
                    NC nextNodeConfig = nodeConfigMap.get(toId);
                    if (nextNodeConfig == null) {
                        throw new SimpleFlowConfigException('[' + toId + ']' + "not exist");
                    }
                    if (lineConfigListSize == 1) {
                        doIterateFlow(nextNodeConfig, innerContext, branch);
                    } else {
                        SimpleFlowEvent<NC> splitEvent = new SimpleFlowEvent<>();
                        splitEvent.setEventCode(SimpleFlowGivenEvent.SPLIT);
                        splitEvent.setNodeConfig(nextNodeConfig);
                        SimpleFlowBranch<C> newBranch = new SimpleFlowBranch<>(branch.getBranchOrder() + splitCount,
                                line.getContext());
                        newBranch.setCurrentSubFlow(branch.getCurrentSubFlow());
                        newBranch.getSubFlowSet().addAll(branch.getSubFlowSet());
                        innerContext.putContextMap(newBranch);
                        throwEvent(splitEvent, innerContext, newBranch);
                    }
                    splitCount++;
                }
            }
        } else if (type == SimpleFlowNodeType.EVENT && eventAction == SimpleFlowEventAction.THROW) {
            SimpleFlowEvent<NC> throwEvent = new SimpleFlowEvent<>();
            throwEvent.setEventCode(nodeConfig.getCode());
            throwEvent.setNodeConfig(nodeConfig);
            throwEvent(throwEvent, innerContext, branch);
        } else if (type == SimpleFlowNodeType.FLOW) {
            // todo
        } else {
            throw new SimpleFlowConfigException("invalid node type [" + type + ']');
        }
    }

    private void assignResponse(SimpleFlowInnerContext<C, NC, LC, FC> innerContext, C context) {
        Object response = context.getResponse();
        if (response != null) {
            Object existResponse = innerContext.getResponse();
            if (existResponse != null) {
                throw new SimpleFlowConfigException("response only can set once");
            } else {
                innerContext.setResponse(response);
                context.setResponse(null);
            }
        }
    }

    private NC findCatchNodeConfig(SimpleFlowGraphConfig<NC, LC> subFlow) {
        for (NC NC : subFlow.getNodeList()) {
            if (NC.getEventAction() == SimpleFlowEventAction.CATCH) {
                return NC;
            }
        }
        throw new SimpleFlowConfigException("subflow no catch event");
    }

    private void notifyResult(SimpleFlowInnerContext<C, NC, LC, FC> innerContext) {
        innerContext.decrementBranchCount();
        int branchCount = innerContext.gainBranchCount();
        if (branchCount == 0) {
            businessThreadPool.submit(() -> {
                try {
                    resultObserver.notifyResult(innerContext);
                } catch (Exception e) {
                    //do nothing
                }
            });
        }
    }

}
