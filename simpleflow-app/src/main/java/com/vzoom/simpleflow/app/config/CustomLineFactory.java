package com.vzoom.simpleflow.app.config;

import com.vzoom.simpleflow.app.bo.CustomLineConfig;
import com.vzoom.simpleflow.app.element.BusinessLine1;
import com.vzoom.simpleflow.core.SimpleFlowAbstractLine;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.factory.SimpleFlowLineFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CustomLineFactory
        implements SimpleFlowLineFactory<CustomLineConfig, SimpleFlowDefaultContext>,
        ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public SimpleFlowAbstractLine<CustomLineConfig, SimpleFlowDefaultContext> build(String flowCode, String nodeId, CustomLineConfig nodeConfig) {
        return applicationContext.getBean(BusinessLine1.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
