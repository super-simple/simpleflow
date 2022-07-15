package com.vzoom.simpleflow.core.util;

public class Exceptions {

    public static String subMsg(Throwable e) {
        String message = e.getMessage();
        if (message == null) {
            Throwable originException = getOriginException(e);
            message = originException.getClass().getCanonicalName();
        }
        return message;
    }

    public static Throwable getOriginException(Throwable e) {
        Throwable lastCause = e;
        while (true) {
            Throwable cause = lastCause.getCause();
            if (cause == null) {
                break;
            } else {
                lastCause = cause;
            }
        }
        return lastCause;
    }
}
