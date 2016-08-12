package com.icaboalo.yana.presentation.screens.register.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterViewModel {

    private String token;
    @SerializedName("user")
    private UserViewModel mUserViewModel;

    public String getToken() {
        return token;
    }

    public UserViewModel getUserViewModel() {
        return mUserViewModel;
    }
}
