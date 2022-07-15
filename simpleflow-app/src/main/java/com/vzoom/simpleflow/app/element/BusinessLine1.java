package com.vzoom.simpleflow.app.element;

import com.vzoom.simpleflow.app.bo.CustomLineConfig;
import com.vzoom.simpleflow.core.SimpleFlowAbstractLine;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import org.springframework.stereotype.Component;

@Component
public class BusinessLine1 extends SimpleFlowAbstractLine<CustomLineConfig, SimpleFlowDefaultContext> {
    @Override
    public boolean runLine() throws Exception {
        return true;
    }
}
