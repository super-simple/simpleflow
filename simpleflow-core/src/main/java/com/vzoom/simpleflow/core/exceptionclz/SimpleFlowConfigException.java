package com.vzoom.simpleflow.core.exceptionclz;

public class SimpleFlowConfigException extends RuntimeException {
    public SimpleFlowConfigException() {
    }

    public SimpleFlowConfigException(String message) {
        super(message);
    }

    public SimpleFlowConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleFlowConfigException(Throwable cause) {
        super(cause);
    }

    public SimpleFlowConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
