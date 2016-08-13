package com.icaboalo.yana.presentation.screens.register.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 12/08/16.
 */
public class UserViewModel {

    private int id;

    private String email, occupation, location, gender;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("phone_number")
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
