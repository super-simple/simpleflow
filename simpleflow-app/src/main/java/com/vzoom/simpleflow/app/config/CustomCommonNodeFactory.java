package com.vzoom.simpleflow.app.config;

import com.vzoom.simpleflow.app.bo.CustomNodeConfig;
import com.vzoom.simpleflow.app.element.BusinessNode1;
import com.vzoom.simpleflow.core.SimpleFlowAbstractCommonNode;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.factory.SimpleFlowCommonNodeFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CustomCommonNodeFactory
        implements SimpleFlowCommonNodeFactory<CustomNodeConfig, SimpleFlowDefaultContext>
        , ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public SimpleFlowAbstractCommonNode<CustomNodeConfig, SimpleFlowDefaultContext> build(String flowCode, String nodeId, CustomNodeConfig nodeConfig) {
        return applicationContext.getBean(BusinessNode1.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
