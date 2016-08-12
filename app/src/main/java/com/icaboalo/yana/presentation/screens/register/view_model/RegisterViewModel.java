package com.icaboalo.yana.presentation.screens.register.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterViewModel {

    private int id;

    private String email, password, location, occupation, gender;
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

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getOccupation() {
        return occupation;
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
