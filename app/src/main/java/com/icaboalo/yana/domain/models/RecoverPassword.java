package com.icaboalo.yana.domain.models;

/**
 * @author icaboalo on 10/08/16.
 */
public class RecoverPassword {

    private boolean success;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
