package com.icaboalo.yana.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 12/08/16.
 */
public class Register {

    private String token;

    @SerializedName("user")
    private User mUser;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return mUser;
    }
}
