package com.icaboalo.yana.data.exception;

/**
 * @author icaboalo on 09/08/16.
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public DataNotFoundException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}
