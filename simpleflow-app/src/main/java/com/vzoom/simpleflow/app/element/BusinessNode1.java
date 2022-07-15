package com.vzoom.simpleflow.app.element;

import com.vzoom.simpleflow.app.bo.CustomNodeConfig;
import com.vzoom.simpleflow.core.SimpleFlowAbstractCommonNode;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BusinessNode1 extends SimpleFlowAbstractCommonNode<CustomNodeConfig, SimpleFlowDefaultContext> {
    @Override
    public void runNode() throws Exception {
        String id = getId();
        System.out.println(id);
        SimpleFlowDefaultContext context = getContext();
        context.putVariable(id, id);
        Map<String, Object> request = (Map<String, Object>) context.getRequest();
        Object id1 = request.get(id);
        if (id1 != null) {
            throw new RuntimeException();
        }
    }
}
