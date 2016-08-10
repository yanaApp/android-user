package com.icaboalo.yana.data.exception;

/**
 * @author icaboalo on 09/08/16.
 */
public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
    }

    public NetworkConnectionException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkConnectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkConnectionException(Throwable throwable) {
        super(throwable);
    }
}
