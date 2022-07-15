package com.vzoom.simpleflow.app.config;

import com.vzoom.simpleflow.app.bo.CustomFlowConfig;
import com.vzoom.simpleflow.app.bo.CustomLineConfig;
import com.vzoom.simpleflow.app.bo.CustomNodeConfig;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContext;
import com.vzoom.simpleflow.core.SimpleFlowDefaultContextFactory;
import com.vzoom.simpleflow.core.SimpleFlowExecutor;
import com.vzoom.simpleflow.core.SimpleFlowExecutorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleFlowConfiguration {

    @Bean
    public CustomCommonNodeFactory customCommonNodeFactory() {
        return new CustomCommonNodeFactory();
    }

    @Bean
    public CustomLineFactory customLineFactory() {
        return new CustomLineFactory();
    }

    @Bean
    public SimpleFlowDefaultContextFactory simpleFlowDefaultContextFactory() {
        return new SimpleFlowDefaultContextFactory();
    }

    @Bean
    public CustomResultObserver customResultObserver() {
        return new CustomResultObserver();
    }

    @Bean
    public SimpleFlowExecutor<SimpleFlowDefaultContext, CustomNodeConfig,
            CustomLineConfig, CustomFlowConfig>
    simpleFlowExecutor(CustomCommonNodeFactory commonNodeFactory,
                       CustomLineFactory lineFactory,
                       SimpleFlowDefaultContextFactory contextFactory,
                       CustomResultObserver resultObserver) {
        return SimpleFlowExecutorFactory.build(commonNodeFactory, lineFactory, contextFactory, resultObserver);
    }
}
