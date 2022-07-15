package com.vzoom.simpleflow.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzoom.simpleflow.app.bo.CustomFlowConfig;
import com.vzoom.simpleflow.app.bo.CustomLineConfig;
import com.vzoom.simpleflow.app.bo.CustomNodeConfig;
import com.vzoom.simpleflow.core.ResultObserver;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.SimpleFlowInnerContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomResultObserver
        implements
        ResultObserver<SimpleFlowDefaultContext, CustomNodeConfig, CustomLineConfig, CustomFlowConfig> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void notifyResult(SimpleFlowInnerContext<SimpleFlowDefaultContext, CustomNodeConfig, CustomLineConfig, CustomFlowConfig> innerContext) throws Exception {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(innerContext));
    }
}
