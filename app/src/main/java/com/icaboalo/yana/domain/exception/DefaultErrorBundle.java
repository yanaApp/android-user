package com.icaboalo.yana.domain.exception;

/**
 * @author icaboalo on 07/08/16.
 */
public class DefaultErrorBundle implements ErrorBundle{

    public static final String DEFAUL_ERROR_MESSAGE = "Unknown error";

    private final Exception mException;

    public DefaultErrorBundle(Exception exception) {
        mException = exception;
    }


    @Override
    public Exception getException() {
        return mException;
    }

    @Override
    public String getErrorMessage() {
        return (mException != null) ? mException.getMessage() : DEFAUL_ERROR_MESSAGE;
    }
}
