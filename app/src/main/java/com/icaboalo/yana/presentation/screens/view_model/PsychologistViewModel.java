package com.icaboalo.yana.presentation.screens.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 24/01/17.
 */

public class PsychologistViewModel {

    private String name, location;
    @SerializedName("phone_number")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
