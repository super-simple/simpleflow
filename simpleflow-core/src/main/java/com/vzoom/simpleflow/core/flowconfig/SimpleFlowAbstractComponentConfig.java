package com.vzoom.simpleflow.core.flowconfig;

import com.vzoom.simpleflow.core.exceptionclz.SimpleFlowConfigException;
import com.vzoom.simpleflow.core.util.Strings;

public abstract class SimpleFlowAbstractComponentConfig implements SimpleFlowComponentConfig, Comparable<SimpleFlowAbstractComponentConfig> {

    protected String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (Strings.isEmpty(id)) {
            throw new SimpleFlowConfigException("id can not be null");
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleFlowAbstractComponentConfig that = (SimpleFlowAbstractComponentConfig) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(SimpleFlowAbstractComponentConfig o) {
        return this.id.compareTo(o.id);
    }
}
