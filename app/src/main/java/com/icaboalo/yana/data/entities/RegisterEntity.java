package com.icaboalo.yana.data.entities;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;

/**
 * @author icaboalo on 12/08/16.
 */
public class RegisterEntity {

    private String token;
    @SerializedName("user")
    private UserRealmModel mUserRealmModel;

    public String getToken() {
        return token;
    }

    public UserRealmModel getUserRealmModel() {
        return mUserRealmModel;
    }
}
