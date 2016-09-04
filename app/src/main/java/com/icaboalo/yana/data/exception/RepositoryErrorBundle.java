package com.icaboalo.yana.data.exception;

import com.icaboalo.yana.domain.exception.ErrorBundle;

/**
 * @author icaboalo on 09/08/16.
 */
public class RepositoryErrorBundle implements ErrorBundle {

    private final Exception mException;

    public RepositoryErrorBundle(Exception exception) {
        mException = exception;
    }

    @Override
    public Exception getException() {
        return mException;
    }

    @Override
    public String getErrorMessage() {
        if (mException != null)
            return mException.getMessage();
        return "";
    }
}
