package com.icaboalo.yana.domain.models;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.domain.models.action_plan.User;

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
