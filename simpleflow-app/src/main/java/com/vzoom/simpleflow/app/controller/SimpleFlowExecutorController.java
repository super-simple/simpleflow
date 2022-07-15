package com.vzoom.simpleflow.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vzoom.simpleflow.app.bo.CustomFlowConfig;
import com.vzoom.simpleflow.app.bo.CustomLineConfig;
import com.vzoom.simpleflow.app.bo.CustomNodeConfig;
import com.vzoom.simpleflow.app.util.Configs;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.SimpleFlowExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("simpleflow")
public class SimpleFlowExecutorController {

    @Autowired
    private SimpleFlowExecutor<SimpleFlowDefaultContext, CustomNodeConfig,
            CustomLineConfig, CustomFlowConfig> simpleFlowExecutor;


    @PostMapping("execute")
    public void execute(@RequestBody Map<String, Object> request) {
        String code = (String) request.get("code");
        CustomFlowConfig config = Configs.getProcessConfig(code, new TypeReference<CustomFlowConfig>() {
        });
        String id = UUID.randomUUID().toString();
        simpleFlowExecutor.runFlow(id, config, request.get("request"));
    }
}
